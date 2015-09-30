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

import com.baloise.testautomation.taf.base._interfaces.IComponent;
import com.baloise.testautomation.taf.base._interfaces.IElement;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByCustom;
import com.baloise.testautomation.taf.common.interfaces.ISwElement;

/**
 * 
 */
public abstract class AElement implements IElement {

  protected Annotation by = null;
  protected Annotation check = null;
  protected IComponent component = null;
  protected String name = "";

  public Object brFind() {
    assertNotNull(
        "component may not be null --> check, if the used annotion is supported (ABase --> getSupportedBys() --> "
            + name, component);
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
  
  // public Region roFind() {
  // // TODO
  // return null;
  // }

  @Override
  public void setBy(Annotation by) {
    this.by = by;
  }

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
    assertNotNull(
        "component may not be null --> check, if the used annotion is supported (ABase --> getSupportedBys() --> "
            + name, component);
    Object element = component.getSwingFinder().find(by);
    assertNotNull("swing element NOT found: " + name, element);
    // TODO --> Hier wurde noch der Finder auf das Element weitergegeben. Muss das auch für element
    // gemacht werden?
    // new SwingElement(component.getSwingFinder(), l)

    try {
      ISwElement<?> swElement = (ISwElement<?>)element;
      assertEquals("wrong type", type.toLowerCase(), swElement.getType().toLowerCase());
    }
    catch (Exception e) {
      fail("swFind --> wrong class: should be instance of ISwElement, but is " + element.getClass());
    }

    return element;
  }

}
