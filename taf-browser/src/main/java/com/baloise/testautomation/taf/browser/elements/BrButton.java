package com.baloise.testautomation.taf.browser.elements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.baloise.testautomation.taf.base._base.AButton;
import com.baloise.testautomation.taf.base._base.TafError;
import com.baloise.testautomation.taf.common.interfaces.IFinder;

public class BrButton extends AButton {

    @Override
    public void click() {
        getFinder().safeInvoke(new TafError(getName() + " -> error when clicking", by), () -> find().click());
    }

    @Override
    public WebElement find() {
        return (WebElement) brFind();
    }

    public WebDriver getDriver() {
      IFinder<?> finder = getFinder();
      if (finder instanceof BrFinder) {
        return ((BrFinder)finder).getDriver();
      }
      return null;
    }

}
