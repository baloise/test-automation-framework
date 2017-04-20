/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.client.elements;

import com.baloise.testautomation.taf.base._interfaces.IData;
import com.baloise.testautomation.taf.base._interfaces.IType;
import com.baloise.testautomation.taf.base.types.TafString;

/**
 * 
 */
public class SwStringInput extends ASwInput implements IData<TafString> {

  @Override
  protected IType asCorrectType(String s) {
    return TafString.normalString(s);
  }

  @Override
  public TafString get() {
    String text = find().getText();
    return new TafString(text);
  }

  @Override
  public Class<TafString> getDataTypeClass() {
    return TafString.class;
  }

  @Override
  public void setCheck(String s) {
    setCheck(new TafString(s));
  }

  @Override
  public void setCheck(TafString value) {
    checkValue = value;
  }

  @Override
  public void setFill(String s) {
    setFill(new TafString(s));
  }

  @Override
  public void setFill(TafString value) {
    fillValue = value;
  }
  
  public void fill(String value) {
    setFill(value);
    fill();
  }

}
