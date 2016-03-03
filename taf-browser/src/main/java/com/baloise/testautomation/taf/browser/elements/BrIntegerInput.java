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
import com.baloise.testautomation.taf.base._interfaces.IType;
import com.baloise.testautomation.taf.base.types.TafInteger;

/**
 * 
 */
public class BrIntegerInput extends ABrInput implements IData<TafInteger> {

  @Override
  protected IType asCorrectType(String s) {
    return TafInteger.normalInteger(s);
  }

  @Override
  public TafInteger get() {
    String text = find().getAttribute("value");
    return new TafInteger(text);
  }

  @Override
  public Class<TafInteger> getDataTypeClass() {
    return TafInteger.class;
  }

  @Override
  public void setCheck(String s) {
    setCheck(new TafInteger(s));
  }

  @Override
  public void setCheck(TafInteger value) {
    checkValue = value;
  }

  @Override
  public void setFill(String s) {
    setFill(new TafInteger(s));
  }

  @Override
  public void setFill(TafInteger value) {
    fillValue = value;
  }

}
