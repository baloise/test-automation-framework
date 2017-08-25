package com.baloise.testautomation.taf.browser.patchedfirefox;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;

public class PatchedWebElement extends RemoteWebElement {

  public PatchedWebElement(RemoteWebElement rwe) {
    Field[] rweFields = RemoteWebElement.class.getDeclaredFields();
    for (Field rweField : rweFields) {
      try {
        rweField.setAccessible(true);
        Field localField = RemoteWebElement.class.getDeclaredField(rweField.getName());
        localField.setAccessible(true);
        localField.set(this, rweField.get(rwe));
      }
      catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
        e.printStackTrace();
        Assert.fail("could not create patched web element");
      }
    }
  }
  
  @Override
  // with firefox 55 and selenium 3.5.2, clicking is behaving differently from selenium 2.53.0 and firefox 45
  // hovering to an element before clicking seems to work --> override click functionality
  public void click() {
    Actions a = new Actions(parent);
    a.moveToElement(this).perform();
    super.click();
  }

}
