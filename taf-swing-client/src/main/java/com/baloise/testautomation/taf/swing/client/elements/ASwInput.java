/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.client.elements;

import com.baloise.testautomation.taf.base._base.AInput;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwInput;

import static com.baloise.testautomation.taf.base._base.TafAssert.assertEquals;

/**
 * 
 */
public abstract class ASwInput extends AInput {

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
        ISwInput<?> se = find();
        se.click();
        se.clear();
        se.enterText(fillValueAsString());
      }
    }
  }

  // @Override
  public ISwInput<?> find() {
    return (ISwInput<?>)swFind(ISwInput.type);
  }

  public boolean isEnabled() {
    return find().isEnabled();
  }
}
