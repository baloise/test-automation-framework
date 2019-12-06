package com.baloise.testautomation.taf.browser;

import com.baloise.testautomation.taf.base._base.ABase;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations;
import com.baloise.testautomation.taf.browser.elements.BrButton;
import com.baloise.testautomation.taf.browser.elements.BrFinder;
import com.baloise.testautomation.taf.common.interfaces.IFinder;
import org.awaitility.Awaitility;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class StaleElementTest extends ABase {

  private final HtmlUnitDriver webDriver = new HtmlUnitDriver(true);
  private final BrFinder brFinder = new BrFinder(webDriver);

  @IAnnotations.ById("ReplaceButton")
  private BrButton replaceButton;

  @IAnnotations.ById("ButtonBeingReplaced")
  private MockBrButton buttonBeingReplaced;

  private WebElement originalButtonElement;

  @Before
  public void setUp() {
    String pathToHtmlFile = this.getClass().getResource("/testPages/browser/StaleElementTestPage.html").getPath();
    webDriver.get("file://" + pathToHtmlFile);
    originalButtonElement = buttonBeingReplaced.find();
  }

  @Test
  public void safeClickTest() throws InterruptedException {
    buttonBeingReplaced.safeClick(this);
    assertEquals(
        "Expecting the first invocation to fail, the safe invocation to retry, then the second invocation to be successful",
        2,
        buttonBeingReplaced.getFindInvocationCount());
  }

  @Test(expected = StaleElementReferenceException.class)
  public void unsafeClickTest() {
    buttonBeingReplaced.unsafeClick(this);
  }

  @Override
  public IFinder<?> getBrowserFinder() {
    return brFinder;
  }

  /**
   * Replace the button in the DOM, making previously acquired WebElement references stale.
   */
  public void makeButtonWebElementStale() {
    replaceButton.click();
    Awaitility.await()
              .atMost(10L, TimeUnit.SECONDS)
              .until(() -> !buttonBeingReplaced.find().equals(originalButtonElement));
  }

}
