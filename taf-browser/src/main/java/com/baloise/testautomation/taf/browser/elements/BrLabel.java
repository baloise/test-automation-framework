package com.baloise.testautomation.taf.browser.elements;

import org.openqa.selenium.WebElement;

import com.baloise.testautomation.taf.base._base.ALabel;
import com.baloise.testautomation.taf.base.types.TafString;

/**
 * 
 */
public class BrLabel extends ALabel {

  @Override
  public void click() {
    getFinder().safeInvoke(() -> find().click());
  }

  // @Override
  public WebElement find() {
    return (WebElement)brFind();
  }

  @Override
  public TafString get() {
    String text = getFinder().safeInvoke(() -> find().getText());
    return new TafString(text);
  }
}
