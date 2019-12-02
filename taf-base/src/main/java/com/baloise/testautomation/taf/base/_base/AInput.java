package com.baloise.testautomation.taf.base._base;

import com.baloise.testautomation.taf.base._interfaces.IType;

public abstract class AInput extends AElement {

  protected IType fillValue = null;
  protected IType checkValue = null;

  protected abstract IType asCorrectType(String s);

  public abstract void fill();
  
  public boolean canCheck() {
    if (checkValue == null) {
      return false;
    }
    return !checkValue.isSkip() && checkValue.isNotNull();
  }

  public boolean canFill() {
    if (fillValue == null) {
      return false;
    }
    return !fillValue.isSkip() && fillValue.isNotNull();
  }

  public IType checkValue() {
    return checkValue;
  }

  public String checkValueAsString() {
    if (checkValue != null) {
      return checkValue.asString();
    }
    return null;
  }

  public void fillWith(String value) {
    fillValue = asCorrectType(value);
    fill();
  }

  public IType fillValue() {
    return fillValue;
  }

  public String fillValueAsString() {
    if (fillValue != null) {
      return fillValue.asString();
    }
    return null;
  }

}
