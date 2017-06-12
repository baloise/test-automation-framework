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
import com.baloise.testautomation.taf.base._interfaces.IType;
import com.baloise.testautomation.taf.base.types.TafString;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTextArea;

/**
 * 
 */
public class SwTextArea extends AInput implements IData<TafString> {

  public void check() {
    if (checkValue != null) {
      if (!checkValue.isSkip() && checkValue.isNotNull()) {
        String text = find().getText();
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
        ISwTextArea<?> se = find();
        se.click();
        se.clear();
        se.enterText(fillValueAsString());
      }
    }
  }

  // @Override
  public ISwTextArea<?> find() {
    return (ISwTextArea<?>)swFind(ISwTextArea.type);
  }

  public boolean isEnabled() {
    return find().isEnabled();
  }

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
