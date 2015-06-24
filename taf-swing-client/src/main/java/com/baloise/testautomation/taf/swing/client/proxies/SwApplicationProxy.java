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
import com.baloise.testautomation.taf.swing.base._interfaces.ISwInput;
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

  public Long getReference() {
    return id;
  }
  
  public String getType() {
    return ISwApplication.type;
  }

  @Override
  public void startJNLPInstrumentation(String url) {
    deleteFor(getReference().intValue());
    TafProperties props = new TafProperties();
    props.putObject("id", getReference());
    startJNLP(url);
    startCommandAndWait(0, ISwApplication.type, Command.startinstrumentation.toString(), props);
    props = SwCommandProperties.getTafPropertiesForId(0);
    if (!"started".equalsIgnoreCase(props.getString("status"))) {
      Assert.fail("application is not instrumented: " + getReference());
    }
    deleteFor(0);
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
    props.putObject("xpath", xpath);
    props.putObject("root", root);
    props = execCommand(ISwApplication.type, Command.findelementbyxpath.toString(), props);
    ISwElement<Long> element = createElement(props.getString("type"));
    if (element != null) {
      element.setReference(props.getLong("id"));
    }
    return element;
  }

  /** 
   * {@inheritDoc}
   */
  @Override
  public List<ISwElement<Long>> findElementsByXpath(Long root, String xpath) {
    Vector<ISwElement<Long>> result = new Vector<>();
    TafProperties props = new TafProperties();
    props.putObject("xpath", xpath);
    props.putObject("root", root);
    props = execCommand(ISwApplication.type, Command.findelementsbyxpath.toString(), props);
    for (String key : props.keySet()) {
      ISwElement<Long> element = createElement(props.getString(key));
      if (element != null) {
        try {
          element.setReference(new Long(Long.parseLong(key)));
          result.add(element);
        } catch (Exception e ) {}
      }
    }
    return result;
  }

  public Hashtable<String, Class<?>> supportedElements() {
    Hashtable<String, Class<?>> supportedElements = new Hashtable<>();
    supportedElements.put(ISwInput.type.toLowerCase(), SwInputProxy.class);
    return supportedElements;
  }

  @SuppressWarnings("unchecked")
  public ISwElement<Long> createElement(String type) {
    Class<?> c = supportedElements().get(type.toLowerCase());
    if (c == null) {
      // TODO --> Richtigen Typ instantiieren
      return null;
    }
    ISwElement<Long> element = null; 
    try {
      element = (ISwElement<Long>)c.newInstance();
    }
    catch (Exception e) {}
    return element;
  }

  public TafProperties startJNLP(String url) {
    //prepareStartInstrumentation();
    Process p = null;
    try {
      p = Runtime.getRuntime().exec("cmd /C \"javaws " + url + "\"");
      p.waitFor();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    //waitUntilInstrumentationStarted();
    return new TafProperties();
  }

  @Override
  public TafProperties execCommand(String type, String command, TafProperties props) {
    startCommandAndWait(getReference().intValue(), type, command, props);
    return SwCommandProperties.getTafPropertiesForId(getReference().intValue());
  }

  private void deleteFor(int id) {
    SwCommandProperties.deleteCommandPropertiesForId(id);
    SwCommand.deleteCommandsForId(id);
  }

  private void startCommandAndWait(int id, String type, String command, TafProperties props) {
    startCommand(id, type, command, props);
    waitUntilDone(id, ISwApplication.Command.execute.toString());
  }
  
  private void startCommand(int id, String t, String command, TafProperties props) {
    deleteFor(id);
    props.putObject("type", t);
    props.putObject("command", command);
    SwCommand c = new SwCommand(id, Command.execute.toString());
    c.insert();
    SwCommandProperties.insertForId(id, props);
    c.setToReady();
  }

  public void waitUntilDone(int id, String type) {
    long time = System.currentTimeMillis();
    while (!SwCommand.isAllDone(id, type)) {
      if (System.currentTimeMillis() > time + 1000 * timeoutInSeconds) {
        throw new SwTimeout();
      }
    }
  }

  
}
