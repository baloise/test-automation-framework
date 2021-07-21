package com.baloise.testautomation.taf.browser.elements;

import org.openqa.selenium.WebElement;

import com.baloise.testautomation.taf.base._base.AButton;
import com.baloise.testautomation.taf.base._base.TafError;

public class BrButton extends AButton {

    @Override
    public void click() {
        getFinder().safeInvoke(new TafError(getName() + " -> error when clicking", by), () -> find().click());
    }

    @Override
    public WebElement find() {
        return (WebElement) brFind();
    }

}
