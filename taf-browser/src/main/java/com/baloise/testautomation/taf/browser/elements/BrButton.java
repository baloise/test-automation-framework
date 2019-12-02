package com.baloise.testautomation.taf.browser.elements;

import org.openqa.selenium.WebElement;

import com.baloise.testautomation.taf.base._base.AButton;

public class BrButton extends AButton {

  @Override
  public void click() {
    find().click();
  }

  // @Override
  public WebElement find() {
    return (WebElement)brFind();
  }

}
