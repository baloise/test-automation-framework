/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.browser.elements;

import com.baloise.testautomation.taf.base._interfaces.IRadiobutton;

/**
 * 
 */
public class ABrRadiobutton extends ABrInput implements IRadiobutton {

  @Override
  public boolean isSelected() {
    return find().isSelected();
  }

  @Override
  public void select() {
    find().click();
  }

}
