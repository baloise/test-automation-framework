/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.browser.elements;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.baloise.testautomation.taf.base._base.AInput;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.Check;

/**
 * 
 */
public abstract class ABrInput extends AInput {

  private static final int MAX_RETRIES = 5;

  public void check() {
    if (checkValue != null) {
      if (!checkValue.isSkip() && checkValue.isNotNull()) {
        String text = null;
        try {
          int timeout = new Double(((Check)check).timeout() * 1000).intValue();
          if (timeout > 0) {
            long time = System.currentTimeMillis();
            while (System.currentTimeMillis() < time + timeout) {
              String tempText = find().getAttribute("value");
              if (checkValue.asString().equals(tempText)) {
                text = tempText;
                break;
              }
            }
          }
        }
        catch (Exception e) {}
        if (text == null) {
          text = find().getAttribute("value");
        }
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
        find().click();
        find().clear();
        find().sendKeys(fillValueAsString());
        find().sendKeys(Keys.TAB);
      }
    }
  }

  // @Override
  public WebElement find() {
    return (WebElement)brFind();
  }

}
