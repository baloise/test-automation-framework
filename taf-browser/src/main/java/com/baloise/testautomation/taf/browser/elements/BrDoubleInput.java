package com.baloise.testautomation.taf.browser.elements;

import com.baloise.testautomation.taf.base._interfaces.IData;
import com.baloise.testautomation.taf.base._interfaces.IType;
import com.baloise.testautomation.taf.base.types.TafDouble;

public class BrDoubleInput extends ABrInput implements IData<TafDouble> {

  @Override
  protected IType asCorrectType(String s) {
    return TafDouble.normalDouble(s);
  }

  @Override
  public TafDouble get() {
    String text = find().getAttribute("value");
    return new TafDouble(text);
  }

  @Override
  public Class<TafDouble> getDataTypeClass() {
    return TafDouble.class;
  }

  @Override
  public void setCheck(String s) {
    setCheck(new TafDouble(s));
  }

  @Override
  public void setCheck(TafDouble value) {
    checkValue = value;
  }

  @Override
  public void setFill(String s) {
    setFill(new TafDouble(s));
  }

  @Override
  public void setFill(TafDouble value) {
    fillValue = value;
  }

}
