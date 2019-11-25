package com.baloise.testautomation.taf.browser.elements;

import org.openqa.selenium.WebElement;

import com.baloise.testautomation.taf.base._base.AElement;

public class BrElement extends AElement {

  @Override
  public void click() {
    getFinder().safeInvoke(() -> find().click());
  }

  public String getText() {
    return getFinder().safeInvoke(() -> find().getText());
  }
  
  public WebElement find() {
    return (WebElement)brFind();
  }

}
