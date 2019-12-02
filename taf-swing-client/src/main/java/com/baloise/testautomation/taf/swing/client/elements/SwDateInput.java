package com.baloise.testautomation.taf.swing.client.elements;

import com.baloise.testautomation.taf.base._interfaces.IData;
import com.baloise.testautomation.taf.base._interfaces.IType;
import com.baloise.testautomation.taf.base.types.TafDate;

public class SwDateInput extends ASwInput implements IData<TafDate> {

  @Override
  protected IType asCorrectType(String s) {
    return TafDate.normalDate(s);
  }

  @Override
  public TafDate get() {
    String text = find().getText();
    return new TafDate(text);
  }

  @Override
  public Class<TafDate> getDataTypeClass() {
    return TafDate.class;
  }

  @Override
  public void setCheck(String s) {
    setCheck(new TafDate(s));
  }

  @Override
  public void setCheck(TafDate value) {
    checkValue = value;
  }

  @Override
  public void setFill(String s) {
    setFill(new TafDate(s));
  }

  @Override
  public void setFill(TafDate value) {
    fillValue = value;
  }

}
