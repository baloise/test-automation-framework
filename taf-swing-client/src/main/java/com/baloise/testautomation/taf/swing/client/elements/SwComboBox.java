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

import com.baloise.testautomation.taf.base._base.AInput;
import com.baloise.testautomation.taf.base._interfaces.IData;
import com.baloise.testautomation.taf.base.types.TafString;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwComboBox;

/**
 * 
 */
public class SwComboBox extends AInput implements IData<TafString> {

  @Override
  public void check() {
    if (checkValue != null) {
      if (!checkValue.isSkip() && checkValue.isNotNull()) {
        String text = get().asString();
        assertEquals("value does NOT match: " + name, checkValue.asString(), text);
      }
    }
  }

  @Override
  public void click() {
    find().click();
  }

  public void fill() {
    if (fillValue != null) {
      if (!fillValue.isSkip() && fillValue.isNotNull()) {
        ISwComboBox<?> se = find();
        se.selectItem(fillValueAsString());
      }
    }
  }
  
  public void selectIndex(int index) {
    ISwComboBox<?> se = find();
    se.selectIndex(index);
  }

  public ISwComboBox<?> find() {
    return (ISwComboBox<?>)swFind(ISwComboBox.type);
  }

  @Override
  public TafString get() {
    String text = find().getSelectedItem();
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

}
