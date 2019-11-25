package com.baloise.testautomation.taf.browser.elements;

import com.baloise.testautomation.taf.base._interfaces.IData;
import com.baloise.testautomation.taf.base._interfaces.IType;
import com.baloise.testautomation.taf.base.types.TafDate;

public class BrDateInput extends ABrInput implements IData<TafDate> {

  @Override
  protected IType asCorrectType(String s) {
    return TafDate.normalDate(s);
  }

  @Override
  public TafDate get() {
    String text = getFinder().safeInvoke(() -> find().getAttribute("value"));
    return new TafDate(text);
  }

  @Override
  public Class<TafDate> getDataTypeClass() {
    return TafDate.class;
  }

  @Override
  public void setCheck(String value) {
    setCheck(new TafDate(value));
  }

  @Override
  public void setCheck(TafDate value) {
    checkValue = value;
  }

  @Override
  public void setFill(String value) {
    setFill(new TafDate(value));
  }

  @Override
  public void setFill(TafDate value) {
    fillValue = value;
  }

}
