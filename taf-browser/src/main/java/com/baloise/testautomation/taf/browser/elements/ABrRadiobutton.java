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
import com.baloise.testautomation.taf.base._interfaces.IType;
import com.baloise.testautomation.taf.base.types.TafBoolean;

/**
 * 
 */
public abstract class ABrRadiobutton extends ABrInput implements IRadiobutton {

  @Override
  protected IType asCorrectType(String s) {
    return TafBoolean.normalBoolean(s);
  }

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
