/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.browser.elements;

import com.baloise.testautomation.taf.base._interfaces.ICheckbox;

/**
 * 
 */
public abstract class ABrCheckbox extends ABrInput implements ICheckbox {

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
