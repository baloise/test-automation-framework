/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.client.elements;

import com.baloise.testautomation.taf.base._base.AElement;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwMenuItem;

/**
 * 
 */
public class SwMenuItem extends AElement {

  @Override
  public void click() {
    find().click();
  }

  public ISwMenuItem<?> find() {
    return (ISwMenuItem<?>)swFind(ISwMenuItem.type);
  }
}
