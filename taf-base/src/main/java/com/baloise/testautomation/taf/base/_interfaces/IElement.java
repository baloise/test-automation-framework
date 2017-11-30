/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.base._interfaces;

import java.lang.annotation.Annotation;

/**
 * 
 */
public interface IElement {

  public void click();

  // public Object find();

  public void setBy(Annotation by);

  public Annotation getBy();

  public void setCheck(Annotation check);

  public void setComponent(IComponent component);

  public void setName(String name);

}
