/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.browser.elements;

import com.baloise.testautomation.taf.base._interfaces.IData;
import com.baloise.testautomation.taf.base.types.TafBoolean;

/**
 * 
 */
public class BrRadiobutton extends ABrRadiobutton implements IData<TafBoolean> {

  @Override
  public void check() {
    if (checkValue != null) {
      // TODO
    }
  }

  @Override
  public void fill() {
    if (fillValue != null) {
      if (!fillValue.isSkip() && fillValue.isNotNull()) {
        if (fillValue.asBoolean()) {
          select();
        }
      }
    }
  }

  @Override
  public TafBoolean get() {
    return new TafBoolean(isSelected());
  }

  @Override
  public Class<TafBoolean> getDataTypeClass() {
    return TafBoolean.class;
  }

  @Override
  public void setCheck(String value) {
    setCheck(TafBoolean.normalBoolean(value));
  }

  @Override
  public void setCheck(TafBoolean value) {
    checkValue = value;
  }

  @Override
  public void setFill(String value) {
    setFill(TafBoolean.normalBoolean(value));
  }

  @Override
  public void setFill(TafBoolean value) {
    fillValue = value;
  }

}
