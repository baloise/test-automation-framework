package com.baloise.testautomation.taf.browser.elements;

import org.openqa.selenium.WebElement;

import com.baloise.testautomation.taf.base._base.AElement;

import static com.baloise.testautomation.taf.base._base.TafAssert.assertTrue;

public class BrLink extends AElement {

  @Override
  public void click() {
    getFinder().safeInvoke(() -> find().click());
  }

  // @Override
  public WebElement find() {
    return getFinder().safeInvoke(() -> {
      WebElement link = (WebElement) brFind();
      assertTrue("", "a".equalsIgnoreCase(link.getTagName()));
      return link;
    });
  }

}
