package com.baloise.testautomation.taf.swing.client.proxies;

import static org.junit.Assert.fail;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByLeftLabel;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByText;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByXpath;
import com.baloise.testautomation.taf.common.interfaces.ISwApplication;
import com.baloise.testautomation.taf.common.interfaces.ISwElement;
import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwButton;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwCheckBox;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwComboBox;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwDialog;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwInput;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwInternalFrame;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwLabel;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwList;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwMenuItem;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwRadioButton;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTabbedPane;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTable;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTextArea;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTree;
import com.baloise.testautomation.taf.swing.base.client.interaction.InteractionController;
import com.baloise.testautomation.taf.swing.base.client.interaction.MockInteractionController;
import com.baloise.testautomation.taf.swing.base.client.interaction.RealInteractionController;

public final class SwApplicationProxy implements ISwApplication<ISwElement<Long>> {

  private static Logger logger = LoggerFactory.getLogger(SwApplicationProxy.class);

  private static InteractionController interactionController = RealInteractionController.withoutJournal();

  private Long id = 0l;

  public int serverTimeoutInMsecs = 50000;

  private boolean failOnCommandErrors = false;

  public SwApplicationProxy(Long id) {
    this.id = id;
  }

  public ISwElement<Long> basicFind(Long root, Annotation annotation) {
    if (annotation instanceof ByLeftLabel) {
      return findByLeftLabel(root, (ByLeftLabel)annotation);
    }
    if (annotation instanceof ByText) {
      return findByText(root, (ByText)annotation);
    }
    if (annotation instanceof ByXpath) {
      return findByXpath(root, (ByXpath)annotation);
    }
    fail("annotation not yet supported: " + annotation.annotationType());
    return null;
  }

  @SuppressWarnings("unchecked")
  public ISwElement<Long> createElement(String type) {
    Class<?> c = supportedElements().get(type.toLowerCase());
    Assert.assertNotNull("type not supported: " + type, c);
    ISwElement<Long> element = null;
    try {
      element = (ISwElement<Long>)c.newInstance();
      element.setApplication(this);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return element;
  }

  private void deleteFor(int id) {
    interactionController.deleteCommandPropertiesForId(id);
    interactionController.deleteCommandsForId(id);
  }

  @Override
  public TafProperties execCommand(TafProperties props) {
    startCommandAndWait(getReference().intValue(), props);
    TafProperties returnProperties = interactionController.getTafPropertiesForId(getReference().intValue());
    checkCommandForErrors(returnProperties);
    return returnProperties;
  }

  private void checkCommandForErrors(TafProperties returnProperties) {
    if (!getFailOnCommandErrors()) {
      return;
    }
    if (returnProperties == null) {
      return;
    }
    String status = returnProperties.getString("status");
    if (status == null) {
      return;
    }
    if (status.equals("error")) {
      throw new CommandException(returnProperties);
    }
  }

  @Override
  public ISwElement<Long> find(Annotation annotation) {
    return find(null, annotation);
  }

  @Override
  public ISwElement<Long> find(ISwElement<Long> root, Annotation annotation) {
    if (root == null) {
      return basicFind(null, annotation);
    }
    return basicFind((Long)root.getReference(), annotation);
  }

  public ISwElement<Long> findByLeftLabel(Long root, ByLeftLabel annotation) {
    List<ISwElement<Long>> tids = findElementsByXpath(root, "//*[@leftlabel=\"" + annotation.value() + "\"]");
    if (tids.size() > annotation.index()) {
      return tids.get(annotation.index());
    }
    return null;
  }

  public ISwElement<Long> findByText(Long root, ByText annotation) {
    List<ISwElement<Long>> tids = findElementsByXpath(root, "//*[contains(@text, '" + annotation.value() + "')]");
    if (tids.size() > annotation.index()) {
      return tids.get(annotation.index());
    }
    return null;
  }

  public ISwElement<Long> findByXpath(Long root, ByXpath annotation) {
    List<ISwElement<Long>> tids = findElementsByXpath(root, annotation.value());
    if (tids.size() > annotation.index()) {
      return tids.get(annotation.index());
    }
    return null;
  }

  private TafProperties execApplicationCommand(Long root, String xpath, Command command, Long timeoutInMsecs) {
    TafProperties props = new TafProperties();
    props.putObject(paramXPath, xpath);
    props.putObject(paramRoot, root);
    props.putObject(paramCommand, command.toString());
    props.putObject(paramType, ISwApplication.type);
    props.putObject(paramTimeout, timeoutInMsecs);
    props = execCommand(props);
    return props;
  }

  private TafProperties execApplicationCommand(Long root, String xpath, Command command) {
    return execApplicationCommand(root, xpath, command, new Long(serverTimeoutInMsecs));
  }

  @Override
  public ISwElement<Long> findElementByXpath(Long root, String xpath) {
    TafProperties props = execApplicationCommand(root, xpath, Command.findelementbyxpath);
    for (String key : props.keySet()) {
      ISwElement<Long> element = getElement(props, key);
      if (element != null) {
        return element;
      }
    }
    return null;
  }

  @Override
  public List<ISwElement<Long>> findElementsByXpath(Long root, String xpath) {
    List<ISwElement<Long>> result = new ArrayList<ISwElement<Long>>();
    TafProperties props = execApplicationCommand(root, xpath, Command.findelementsbyxpath);
    for (String key : props.keySet()) {
      ISwElement<Long> element = getElement(props, key);
      if (element != null) {
        result.add(element);
      }
    }

    // Order elements according to their TID, to fit ordering in swing hierarchy
    Comparator<ISwElement<Long>> comparator = new Comparator<ISwElement<Long>>() {

      @Override
      public int compare(ISwElement<Long> element1, ISwElement<Long> element2) {
        return (element1.getReference().intValue() - element2.getReference().intValue());
      }

    };
    Collections.sort(result, comparator);
    return result;
  }

  private ISwElement<Long> getElement(TafProperties props, String key) {
    logger.debug("getElement: " + props.toString());
    ISwElement<Long> element = createElement(props.getString(key));
    if (element != null) {
      try {
        element.setReference(new Long(Long.parseLong(key)));
        return element;
      }
      catch (Exception e) {}
    }
    return null;
  }

  public Long getReference() {
    return id;
  }

  public String getType() {
    return ISwApplication.type;
  }

  @Override
  public void sendKeys(String keys) {
    TafProperties props = new TafProperties();
    props.putObject(paramKeys, keys);
    props.putObject(paramCommand, Command.sendkeys.toString());
    props.putObject(paramType, ISwApplication.type);
    props = execCommand(props);
  }

  public void start(String commandline) {
    interactionController.startApplication(commandline);
  }

  public void stop(String jnlp) {
    interactionController.stopApplication(jnlp);
  }


  private void startCommand(int id, TafProperties props) {
    interactionController.startCommand(id, props);
  }

  private void startCommandAndWait(int id, TafProperties props) {
    startCommand(id, props);
    waitUntilDone(id);
  }

  @Override
  public void startInstrumentation(String commandline, String javaClassPathContains, String sunJavaCommandContains) {
    interactionController.init();
    logger.info("Starting instrumentation with commandline: " + commandline + "; Java classpath: "
        + javaClassPathContains + "; Java command: " + sunJavaCommandContains);
    deleteFor(getReference().intValue());
    TafProperties props = new TafProperties();
    props.putObject(paramId, getReference());
    props.putObject(paramCommand, ISwApplication.Command.startinstrumentation);
    props.putObject(paramJavaClassPath, javaClassPathContains);
    props.putObject(paramSunJavaCommand, sunJavaCommandContains);
    props.putObject(paramType, ISwApplication.type.toString());
    startCommand(0, props);
    start(commandline);
    waitUntilDone(0);
    // startCommandAndWait(0, props);
    props = interactionController.getTafPropertiesForId(0);
    if (!valueStarted.equalsIgnoreCase(props.getString(paramStatus))) {
      Assert.fail("application is not instrumented: " + getReference());
    }
    deleteFor(0);
  }

  @Override
  public void startInstrumentationWithSpy(String commandline, String javaClassPathContains,
      String sunJavaCommandContains, String filename) {
    logger.info("Starting instrumentation with spy with commandline: " + commandline + "; Java classpath: "
        + javaClassPathContains + "; Java command: " + sunJavaCommandContains + "; Spy file: " + filename);
    interactionController.init();

    deleteFor(getReference().intValue());
    TafProperties props = new TafProperties();
    props.putObject(paramId, getReference());
    props.putObject(paramCommand, ISwApplication.Command.startinstrumentation);
    props.putObject(paramJavaClassPath, javaClassPathContains);
    props.putObject(paramSunJavaCommand, sunJavaCommandContains);
    props.putObject(paramType, ISwApplication.type.toString());
    props.putObject(paramSpy, true);
    props.putObject(paramPath, filename);
    startCommand(0, props);
    start(commandline);
    waitUntilDone(0);
    // startCommandAndWait(0, props);
    props = interactionController.getTafPropertiesForId(0);
    if (!valueStarted.equalsIgnoreCase(props.getString(paramStatus))) {
      Assert.fail("application is not instrumented: " + getReference());
    }
    deleteFor(0);
  }

  @Override
  public void storeHierarchy(String path) {
    TafProperties props = new TafProperties();
    props.putObject(paramCommand, Command.storehierarchy.toString());
    props.putObject(paramType, ISwApplication.type);
    props.putObject(paramPath, path);
    execCommand(props);
  }

  public Hashtable<String, Class<?>> supportedElements() {
    Hashtable<String, Class<?>> supportedElements = new Hashtable<>();
    supportedElements.put(ISwInput.type.toLowerCase(), SwInputProxy.class);
    supportedElements.put(ISwTextArea.type.toLowerCase(), SwTextAreaProxy.class);
    supportedElements.put(ISwDialog.type.toLowerCase(), SwDialogProxy.class);
    supportedElements.put(ISwButton.type.toLowerCase(), SwButtonProxy.class);
    supportedElements.put(ISwComboBox.type.toLowerCase(), SwComboBoxProxy.class);
    supportedElements.put(ISwTable.type.toLowerCase(), SwTableProxy.class);
    supportedElements.put(ISwMenuItem.type.toLowerCase(), SwMenuItemProxy.class);
    supportedElements.put(ISwTabbedPane.type.toLowerCase(), SwTabbedPaneProxy.class);
    supportedElements.put(ISwLabel.type.toLowerCase(), SwLabelProxy.class);
    supportedElements.put(ISwList.type.toLowerCase(), SwListProxy.class);
    supportedElements.put(ISwTree.type.toLowerCase(), SwTreeProxy.class);
    supportedElements.put(ISwCheckBox.type.toLowerCase(), SwCheckBoxProxy.class);
    supportedElements.put(ISwRadioButton.type.toLowerCase(), SwRadioButtonProxy.class);
    supportedElements.put(ISwInternalFrame.type.toLowerCase(), SwInternalFrameProxy.class);
    return supportedElements;
  }

  public void waitUntilDone(int id) {
    interactionController.waitUntilDone(id, clientTimeoutInMsecs());
  }

  public int clientTimeoutInMsecs() {
    return (serverTimeoutInMsecs) + 5000;
  }

  @Override
  public void setDelayBetweenEvents(Long ms) {
    TafProperties props = new TafProperties();
    props.putObject(paramCommand, Command.setdelaybetweenevents.toString());
    props.putObject(paramType, ISwApplication.type);
    props.putObject(paramDelayBetweenEvents, ms);
    execCommand(props);
  }

  @Override
  public void setEventPostingDelay(Long ms) {
    TafProperties props = new TafProperties();
    props.putObject(paramCommand, Command.seteventpostingdelay.toString());
    props.putObject(paramType, ISwApplication.type);
    props.putObject(paramEventPostingDelay, ms);
    execCommand(props);
  }

  @Override
  public void setDelayBetweenKeystrokes(Long ms) {
    TafProperties props = new TafProperties();
    props.putObject(paramCommand, Command.setdelaybetweenkeystrokes.toString());
    props.putObject(paramType, ISwApplication.type);
    props.putObject(paramDelayBetweenKeystrokes, ms);
    execCommand(props);
  }

  public void initInteractionMocking(String path) {
   interactionController = MockInteractionController.newWithJournal(path);
  }

  public void initInteractionJournal() {
    interactionController = RealInteractionController.withJournal();
  }

  public void serializeInteractionJournal(String path) {
    interactionController.serializeJournal(path);
  }

  @Override
  public void setTreeSeparator(String separator) {
    TafProperties props = new TafProperties();
    props.putObject(paramCommand, Command.settreeseparator.toString());
    props.putObject(paramType, ISwApplication.type);
    props.putObject(paramSeparator, separator);
    execCommand(props);
  }

  @Override
  public boolean getFailOnCommandErrors() {
    return failOnCommandErrors;
  }

  public void setFailOnCommandErrors(boolean bool) {
    failOnCommandErrors = bool;
  }

  @Override
  public void setTimeoutInMsecs(Long msecs) {
    if (msecs != null) {
      serverTimeoutInMsecs = msecs.intValue();
    }
  }

  @Override
  public Long getTimeoutInMsecs() {
    return new Long(serverTimeoutInMsecs);
  }

  @Override
  public void setDefaultTimeoutInMsecs() {
    serverTimeoutInMsecs = 50000;
  }

}
