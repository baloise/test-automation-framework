package com.baloise.testautomation.taf.browser.elements;

import com.baloise.testautomation.taf.base._interfaces.ICheckbox;
import com.baloise.testautomation.taf.base._interfaces.IType;
import com.baloise.testautomation.taf.base.types.TafBoolean;

public abstract class ABrCheckbox extends ABrInput implements ICheckbox {

  @Override
  protected IType asCorrectType(String s) {
    return TafBoolean.normalBoolean(s);
  }

  public Boolean fillValueAsPrimitiveBoolean() {
    if (fillValue.isSkip() || fillValue.isEmpty()) {
      return false;
    }
    Boolean b = fillValue.asBoolean();
    if (b == null) {
      return false;
    }
    return b.booleanValue();
  }

  @Override
  public boolean isSelected() {
    return find().isSelected();
  }

  @Override
  public boolean isUnselected() {
    return !isSelected();
  }

  @Override
  public void select() {
    if (isUnselected()) {
      click();
    }
  }

  @Override
  public void select(boolean selection) {
    if (selection) {
      select();
    }
    else {
      unselect();
    }
  }

  @Override
  public void unselect() {
    if (isSelected()) {
      click();
    }
  }
}
