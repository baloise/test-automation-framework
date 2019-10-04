package com.baloise.testautomation.taf.browser.patchedfirefox;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;

import static com.baloise.testautomation.taf.base._base.TafAssert.fail;

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
        fail("could not create patched web element");
      }
    }
  }
  
  @Override
  // with firefox 55 and selenium 3.5.2, clicking is behaving differently from selenium 2.53.0 and firefox 45
  // hovering to an element before clicking seems to work --> override click functionality
  public void click() {
    ((JavascriptExecutor)parent).executeScript("arguments[0].scrollIntoView(true);", this);
    try {
      Actions a = new Actions(parent);
      a.moveToElement(this).perform();
    }
    catch (Exception e) {
      Logger.getGlobal().warning("could not move to web element: " + this);
      e.printStackTrace();
    }
    super.click();
  }

}
