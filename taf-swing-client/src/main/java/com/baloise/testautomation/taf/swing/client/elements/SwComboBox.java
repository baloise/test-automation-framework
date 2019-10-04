/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.client.elements;

import java.util.List;

import com.baloise.testautomation.taf.base._base.AInput;
import com.baloise.testautomation.taf.base._interfaces.IData;
import com.baloise.testautomation.taf.base._interfaces.IType;
import com.baloise.testautomation.taf.base.types.TafString;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwComboBox;

import static com.baloise.testautomation.taf.base._base.TafAssert.assertEquals;

/**
 * 
 */
public class SwComboBox extends AInput implements IData<TafString> {

  @Override
  protected IType asCorrectType(String s) {
    return TafString.normalString(s);
  }

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
        String item = fillValueAsString();
        selectItemUsingDefaultStrategy(item);
      }
    }
  }

  public void selectItemUsingDefaultStrategy(String item) {
    selectItem(item);
  }

  public void selectItem(String item) {
    ISwComboBox<?> se = find();
    se.selectItem(item);
  }
  
  public void selectIndex(Long index) {
    ISwComboBox<?> se = find();
    se.selectIndex(index);
  }
  
  public void selectIndexByMatchingDescription(String item) {
    ISwComboBox<?> se = find();
    se.selectIndexByMatchingDescription(item);
  }
  
  public void selectItemByFillingInput(String item) {
    ISwComboBox<?> se = find();
    se.selectItemByFillingInput(item);
  }
  
  public void selectItemByMatchingDescription(String item) {
    ISwComboBox<?> se = find();
    se.selectItemByMatchingDescription(item);
  }

  public ISwComboBox<?> find() {
    return (ISwComboBox<?>)swFind(ISwComboBox.type);
  }

  @Override
  public TafString get() {
    String text = find().getSelectedItem();
    return new TafString(text);
  }

  public List<String> getAllItems() {
    return find().getAllItems();
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
