/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.base._base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.lang.annotation.Annotation;

import org.junit.Assert;

import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByCustom;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByXpath;
import com.baloise.testautomation.taf.base._interfaces.IComponent;
import com.baloise.testautomation.taf.base._interfaces.IElement;
import com.baloise.testautomation.taf.common.interfaces.IFinder;
import com.baloise.testautomation.taf.common.interfaces.ISwElement;

/**
 * 
 */
public abstract class AElement implements IElement {

  protected Annotation by = null;
  protected Annotation check = null;
  protected IComponent component = null;
  protected String name = "";

  private void assertComponentNotNull() {
    assertNotNull(
        "component may not be null --> check, if the used annotion is supported (ABase --> getSupportedBys() --> "
            + name,
        component);
  }

  protected IFinder<?> getFinder() {
    assertComponentNotNull();
    IFinder<?> brFinder = null;
    IFinder<?> swFinder = null;
    try {
      brFinder = component.getBrowserFinder();
    }
    catch (Throwable t) {}
    try {
      swFinder = component.getSwingFinder();
    }
    catch (Throwable t) {}
    if (brFinder != null && swFinder != null) {
      Assert.fail("more than one finder declared on component --> cannot decide which one to use --> fail");
    }
    if (brFinder != null) {
      return brFinder;
    }
    if (swFinder != null) {
      return swFinder;
    }
    Assert.fail("no finder declared on component --> fail");
    return null;
  }

  public boolean exists(double secsToWait) {
    Long timeoutInMsecs = getFinder().getTimeoutInMsecs();
    boolean result = false;
    try {
      getFinder().setTimeoutInMsecs(new Double(secsToWait * 1000).longValue());
      result = find() != null;
    }
    catch (Throwable t) {}
    finally {
      getFinder().setTimeoutInMsecs(timeoutInMsecs);
    }
    return result;
  }

  public boolean exists() {
    return exists(0.01);
  }

  public void waitForExistence(double seconds) {
    long time = System.currentTimeMillis();
    while (System.currentTimeMillis() <= time + new Double(seconds * 1000).longValue()) {
      if (exists()) {
        return;
      }
    }
    Assert.fail("element after " + seconds + " seconds not found: " + name);
  }
  
  public abstract Object find();

  public Object brFind() {
    assertComponentNotNull();
    Object we = null;
    if (by instanceof ByCustom) {
      we = brFindByCustom();
    }
    if (we == null) {
      we = component.getBrowserFinder().find(by);
    }
    assertNotNull("webelement NOT found: " + name, we);
    return we;
  }

  public Object brFindByCustom() {
    return null;
  }

  @Override
  public void setBy(Annotation by) {
    this.by = by;
  }

  @Override
  public Annotation getBy() {
    return this.by;
  }

  // public Region roFind() {
  // // TODO
  // return null;
  // }

  @Override
  public void setCheck(Annotation check) {
    this.check = check;
  }

  @Override
  public void setComponent(IComponent component) {
    this.component = component;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  public Object swFind(String type) {
    assertComponentNotNull();
    Object element = null;
    if (by instanceof ByCustom) {
      element = swFindByCustom();
    }
    if (element == null) {
      element = component.getSwingFinder().find(by);
    }
    assertNotNull("swing element NOT found: " + name, element);
    try {
      ISwElement<?> swElement = (ISwElement<?>)element;
      assertEquals("wrong type", type.toLowerCase(), swElement.getType().toLowerCase());
    }
    catch (Exception e) {
      fail("swFind --> wrong class: should be instance of ISwElement, but is " + element.getClass());
    }

    return element;
  }

  public Object swFindByCustom() {
    return null;
  }

  public ByXpath getByXpath(final String xpath) {
    ByXpath byXpath = new ByXpath() {

      @Override
      public Class<? extends Annotation> annotationType() {
        return ByXpath.class;
      }

      @Override
      public int index() {
        return 0;
      }

      @Override
      public String value() {
        return xpath;
      }
    };
    return byXpath;
  }

  public void withXPath(IComponent parent, String name, final String xpath) {
    ByXpath byXpath = getByXpath(xpath);
    setBy(byXpath);
    setName(name);
    setComponent(parent);
  }

}
