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
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.junit.Assert;

import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByLeftLabel;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByText;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByXpath;
import com.baloise.testautomation.taf.common.interfaces.ISwApplication;
import com.baloise.testautomation.taf.common.interfaces.ISwElement;
import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwButton;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwComboBox;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwInput;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwMenuItem;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTabbedPane;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTable;
import com.baloise.testautomation.taf.swing.base.db.H2DB;
import com.baloise.testautomation.taf.swing.base.db.SwCommand;
import com.baloise.testautomation.taf.swing.base.db.SwCommandProperties;
import com.baloise.testautomation.taf.swing.base.db.SwTimeout;

/**
 * 
 */
public class SwApplicationProxy implements ISwApplication<ISwElement<Long>> {

  Long id = 0l;

  private int timeoutInSeconds = 50;

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
    catch (Exception e) {}
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
    Vector<ISwElement<Long>> result = new Vector<>();
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

  public TafProperties startJNLP(String url) {
    // prepareStartInstrumentation();
    Process p = null;
    try {
      p = Runtime.getRuntime().exec("cmd /C \"javaws " + url + "\"");
      p.waitFor();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    // waitUntilInstrumentationStarted();
    return new TafProperties();
  }

  @Override
  public void startJNLPInstrumentation(String url) {
    H2DB.init();
    
    deleteFor(getReference().intValue());
    TafProperties props = new TafProperties();
    props.putObject(paramId, getReference());
    props.putObject(paramCommand, ISwApplication.Command.startinstrumentation);
    props.putObject(paramType, ISwApplication.type.toString());
    startJNLP(url);
    startCommandAndWait(0, props);
    props = SwCommandProperties.getTafPropertiesForId(0);
    if (!valueStarted.equalsIgnoreCase(props.getString(paramStatus))) {
      Assert.fail("application is not instrumented: " + getReference());
    }
    deleteFor(0);
  }

  @Override
  public void startJNLPInstrumentationWithSpy(String url, String filename) {
    H2DB.init();
    
    deleteFor(getReference().intValue());
    TafProperties props = new TafProperties();
    props.putObject(paramId, getReference());
    props.putObject(paramCommand, ISwApplication.Command.startinstrumentation);
    props.putObject(paramType, ISwApplication.type.toString());
    props.putObject(paramSpy, true);
    props.putObject(paramPath, filename);
    startJNLP(url);
    startCommandAndWait(0, props);
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
