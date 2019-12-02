package com.baloise.testautomation.taf.browser.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.baloise.testautomation.taf.base._interfaces.ICombobox;
import com.baloise.testautomation.taf.base._interfaces.IData;
import com.baloise.testautomation.taf.base._interfaces.IType;
import com.baloise.testautomation.taf.base.types.TafString;

import static com.baloise.testautomation.taf.base._base.TafAssert.assertEquals;

public class BrCombobox extends ABrInput implements ICombobox, IData<TafString> {

  @Override
  protected IType asCorrectType(String s) {
    return TafString.normalString(s);
  }

  @Override
  public void check() {
    if (checkValue != null) {
      if (checkValue.isCustom()) {
        checkCustom();
        return;
      }
      if (!checkValue.isSkip() && checkValue.isNotNull()) {
        WebElement we = find();
        Select s = new Select(we);
        String text = s.getFirstSelectedOption().getText();
        assertEquals("value does NOT match: " + name, checkValue.asString(), text);
      }
    }
  }

  @Override
  public void fill() {
    if (fillValue != null) {
      if (fillValue.isCustom()) {
        fillCustom();
        return;
      }
      if (!fillValue.isSkip() && fillValue.isNotNull()) {
        WebElement we = find();
        Select s = new Select(we);
        s.selectByVisibleText(fillValue.asString());
      }
    }
  }

  @Override
  public TafString get() {
    WebElement we = find();
    Select s = new Select(we);
    return TafString.normalString(s.getFirstSelectedOption().getText());
  }

  @Override
  public Class<TafString> getDataTypeClass() {
    return TafString.class;
  }

  @Override
  public void setCheck(String value) {
    setCheck(TafString.normalString(value));
  }

  @Override
  public void setCheck(TafString value) {
    this.checkValue = value;
  }

  @Override
  public void setFill(String value) {
    setFill(TafString.normalString(value));
  }

  @Override
  public void setFill(TafString value) {
    this.fillValue = value;
  }
}
