package com.baloise.testautomation.taf.base._base;

import com.baloise.testautomation.taf.base._interfaces.IAnnotations.Check;
import com.baloise.testautomation.taf.base._interfaces.IData;
import com.baloise.testautomation.taf.base._interfaces.IType;
import com.baloise.testautomation.taf.base.types.TafString;

import static com.baloise.testautomation.taf.base._base.TafAssert.assertEquals;
import static com.baloise.testautomation.taf.base._base.TafAssert.fail;

public abstract class ALabel extends AElement implements IData<TafString> {

  protected IType checkValue = null;

  @Override
  public boolean canCheck() {
    if (checkValue == null) {
      return false;
    }
    return !checkValue.isSkip() && checkValue.isNotNull();
  }

  @Override
  public boolean canFill() {
    return false;
  }

  @Override
  public void check() {
    if (canCheck()) {
      String text = null;
      try {
        int timeout = new Double(((Check)check).timeout() * 1000).intValue();
        if (timeout > 0) {
          long time = System.currentTimeMillis();
          while (System.currentTimeMillis() < time + timeout) {
            String tempText = get().asString();
            if (checkValue.asString().equals(tempText)) {
              text = tempText;
              break;
            }
          }
        }
      }
      catch (Exception e) {}
      if (text == null) {
        text = get().asString();
      }
      assertEquals(checkValue.asString(), text);
    }
  }

  public String checkValueAsString() {
    if (checkValue != null) {
      return checkValue.asString();
    }
    return null;
  }

  @Override
  public void fill() {
    fail("label cannot be filled");
  }

  @Override
  public Class<TafString> getDataTypeClass() {
    return TafString.class;
  }

  @Override
  public void setCheck(String value) {
    checkValue = new TafString(value);
  }

  @Override
  public void setCheck(TafString value) {
    checkValue = value;
  }

  @Override
  public void setFill(String value) {
    fail("label cannot have fill value");
  }

  @Override
  public void setFill(TafString value) {
    fail("label cannot have fill value");
  }

}
