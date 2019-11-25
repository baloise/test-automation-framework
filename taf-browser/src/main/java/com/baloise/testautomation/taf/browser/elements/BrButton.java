package com.baloise.testautomation.taf.browser.elements;

import com.baloise.testautomation.taf.base._base.AButton;
import org.openqa.selenium.WebElement;

public class BrButton extends AButton {

  @Override
  public void click() {
    getFinder().safeInvoke(() -> find().click());
  }

  @Override
  public WebElement find() {
    return (WebElement) brFind();
  }

}
