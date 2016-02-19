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

  public Boolean fillValueAsPrimitiveBoolean() {
    if (fillValue.isSkip() || fillValue.isEmpty()) {
      return false;
    }
    Boolean b = fillValue.asBoolean();
    if (b == null) {
      return false;
    }
    return b.booleanValue();
  }

  @Override
  public boolean isSelected() {
    return find().isSelected();
  }

  @Override
  public void select() {
    find().click();
  }

}
