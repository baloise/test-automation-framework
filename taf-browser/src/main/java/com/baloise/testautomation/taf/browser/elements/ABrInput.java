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
              // If element is an input field, then value is needed
              String tempText = find().getAttribute("value");
              // maybe it's just another element (e.g. a <div>) --> try simple getText()
              if (tempText == null) {
                tempText = find().getText();
              }
              if (checkValue.asString().equals(tempText)) {
                text = tempText;
                break;
              }
            }
          }
        }
        catch (Exception e) {}
        // find for non-timeout-case --> if it's an input --> value is needed
        if (text == null) {
          text = find().getAttribute("value");
        }
        // not found? Maybe it's another element --> try simple getText()
        if (text == null) {
          text = find().getText();
        }
        assertEquals("value does NOT match for element '" + name + "': ", checkValue.asString(), text);
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
