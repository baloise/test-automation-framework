package com.baloise.testautomation.taf.browser.interfaces;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.baloise.testautomation.taf.common.interfaces.IFinder;

public interface IBrowserFinder<WebElement> extends IFinder<WebElement> {

  public List<WebElement> findAllBy(WebElement root, By by);

  public WebElement findBy(WebElement root, By by);

  public WebDriver getDriver();

  public void waitUntilLoadingComplete();

}
