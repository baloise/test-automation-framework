/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.client.proxies;

import static org.junit.Assert.fail;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import org.junit.Assert;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByLeftLabel;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByText;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByXpath;
import com.baloise.testautomation.taf.common.interfaces.ISwApplication;
import com.baloise.testautomation.taf.common.interfaces.ISwElement;
import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwButton;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwCheckBox;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwComboBox;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwInput;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwLabel;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwList;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwMenuItem;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwRadioButton;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTabbedPane;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTable;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTree;
import com.baloise.testautomation.taf.swing.base.db.H2DB;
import com.baloise.testautomation.taf.swing.base.db.SwCommand;
import com.baloise.testautomation.taf.swing.base.db.SwCommandProperties;
import com.baloise.testautomation.taf.swing.base.db.SwTimeout;

/**
 * 
 */
public final class SwApplicationProxy implements ISwApplication<ISwElement<Long>> {

  private Long id = 0l;
  private Long delayBetweenEvents = 10L;

  private int timeoutInSeconds = 50;

  public SwApplicationProxy(Long id) {
    this(id, 10L);
  }

  public SwApplicationProxy(Long id, Long delayBetweenEvents) {
    this.id = id;
    this.delayBetweenEvents = delayBetweenEvents;
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
    SwCommandProperties.deleteCommandPropertiesForId(id);
    SwCommand.deleteCommandsForId(id);
  }

  @Override
  public TafProperties execCommand(TafProperties props) {
    startCommandAndWait(getReference().intValue(), props);
    return SwCommandProperties.getTafPropertiesForId(getReference().intValue());
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

  @Override
  public ISwElement<Long> findElementByXpath(Long root, String xpath) {
    TafProperties props = new TafProperties();
    props.putObject(paramXPath, xpath);
    props.putObject(paramRoot, root);
    props.putObject(paramCommand, Command.findelementbyxpath.toString());
    props.putObject(paramType, ISwApplication.type);
    props = execCommand(props);
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
    TafProperties props = new TafProperties();
    props.putObject(paramXPath, xpath);
    props.putObject(paramRoot, root);
    props.putObject(paramCommand, Command.findelementsbyxpath.toString());
    props.putObject(paramType, ISwApplication.type);
    props = execCommand(props);
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
    Process p = null;
    try {
      p = Runtime.getRuntime().exec(commandline);
      p.waitFor();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void startCommand(int id, TafProperties props) {
    deleteFor(id);
    SwCommand c = new SwCommand(id);
    c.insert();
    SwCommandProperties.insertForId(id, props);
    c.setToReady();
  }

  private void startCommandAndWait(int id, TafProperties props) {
    startCommand(id, props);
    waitUntilDone(id);
  }

  @Override
  public void startInstrumentation(String commandline, String javaClassPathContains, String sunJavaCommandContains) {
    H2DB.init();

    deleteFor(getReference().intValue());
    TafProperties props = new TafProperties();
    props.putObject(paramId, getReference());
    props.putObject(paramDelayBetweenEvents, delayBetweenEvents);
    props.putObject(paramCommand, ISwApplication.Command.startinstrumentation);
    props.putObject(paramJavaClassPath, javaClassPathContains);
    props.putObject(paramSunJavaCommand, sunJavaCommandContains);
    props.putObject(paramType, ISwApplication.type.toString());
    startCommand(0, props);
    start(commandline);
    waitUntilDone(0);
    // startCommandAndWait(0, props);
    props = SwCommandProperties.getTafPropertiesForId(0);
    if (!valueStarted.equalsIgnoreCase(props.getString(paramStatus))) {
      Assert.fail("application is not instrumented: " + getReference());
    }
    deleteFor(0);
  }

  @Override
  public void startInstrumentationWithSpy(String commandline, String javaClassPathContains,
      String sunJavaCommandContains, String filename) {
    H2DB.init();

    deleteFor(getReference().intValue());
    TafProperties props = new TafProperties();
    props.putObject(paramId, getReference());
    props.putObject(paramDelayBetweenEvents, delayBetweenEvents);
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
    props = SwCommandProperties.getTafPropertiesForId(0);
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
    return supportedElements;
  }

  public void waitUntilDone(int id) {
    long time = System.currentTimeMillis();
    while (!SwCommand.isAllDone(id)) {
      if (System.currentTimeMillis() > time + 1000 * timeoutInSeconds) {
        throw new SwTimeout();
      }
    }
  }

}
