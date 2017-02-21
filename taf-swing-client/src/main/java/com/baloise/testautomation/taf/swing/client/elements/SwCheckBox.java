/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.client.elements;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;

import com.baloise.testautomation.taf.base._base.AInput;
import com.baloise.testautomation.taf.base._interfaces.IData;
import com.baloise.testautomation.taf.base.types.TafBoolean;
import com.baloise.testautomation.taf.base.types.TafString;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwCheckBox;

/**
 * 
 */
public class SwCheckBox extends AInput implements IData<TafBoolean> {

  @Override
  public void check() {
    if (checkValue != null) {
      if (!checkValue.isSkip() && checkValue.isNotNull()) {
        boolean value = get().asBoolean();
        assertEquals("value does NOT match: " + name, checkValue.asBoolean(), value);
      }
    }
  }

  @Override
  public void click() {
    Assert.fail("Click for Checkboxes not implemented. Use check or uncheck instead.");
  }

  public void fill() {
    if (fillValue != null) {
      if (!fillValue.isSkip() && fillValue.isNotNull()) {
        if (fillValue.asBoolean()) {
          setChecked();
        }
        else {
          setUnchecked();
        }
      }
    }
  }
  
  public void setChecked() {
    ISwCheckBox<?> se = find();
    se.check();
  }
  
  public void setUnchecked() {
    ISwCheckBox<?> se = find();
    se.uncheck();
  }

  public ISwCheckBox<?> find() {
    return (ISwCheckBox<?>)swFind(ISwCheckBox.type);
  }

  @Override
  public TafBoolean get() {
    return new TafBoolean(find().isChecked());
  }

  @Override
  public Class<TafBoolean> getDataTypeClass() {
    return TafBoolean.class;
  }

  @Override
  public void setCheck(String s) {
    setCheck(new TafString(s).asTafBoolean());
  }

  @Override
  public void setCheck(TafBoolean value) {
    checkValue = value;
  }

  @Override
  public void setFill(String s) {
    setFill(new TafString(s).asTafBoolean());
  }

  @Override
  public void setFill(TafBoolean value) {
    fillValue = value;
  }

}
