package com.baloise.testautomation.taf.browser.patchedfirefox;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebElement;

public class PatchedFirefoxDriver extends FirefoxDriver {

  public PatchedFirefoxDriver(FirefoxOptions ffo) {
    super(ffo);
  }

  @Override
  public List<WebElement> findElements(By by) {
    List<WebElement> elements = super.findElements(by);
    List<WebElement> patchedElements = new ArrayList<WebElement>();
    for (WebElement element : elements) {
      patchedElements.add(new PatchedWebElement((RemoteWebElement)element));
    }
    return patchedElements;
  }

  @Override
  public WebElement findElement(By by) {
    return new PatchedWebElement((RemoteWebElement)super.findElement(by));
  }

  @Override
  public void quit() {
    Assert.fail("do not call this method because it doesn't react in the way expected");
  }

  @Override
  public void close() {
    Assert.fail("do not call this method because it doesn't react in the way expected");
  }

}
