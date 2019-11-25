package com.baloise.testautomation.taf.browser;

import com.baloise.testautomation.taf.base._base.ABase;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations;
import com.baloise.testautomation.taf.browser.elements.BrButton;
import com.baloise.testautomation.taf.browser.elements.BrFinder;
import com.baloise.testautomation.taf.common.interfaces.IFinder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore("Unstable: used to try to reproduce a race condition")
public class StaleElementTest extends ABase {

  private static final Logger logger = LoggerFactory.getLogger(StaleElementTest.class);

  private final HtmlUnitDriver webDriver = new HtmlUnitDriver(true);
  private final BrFinder brFinder = new BrFinder(webDriver);

  @IAnnotations.ById("Button")
  private BrButton button;

  @Before
  public void setUp() {
    String pathToHtmlFile = this.getClass()
        .getResource("/StaleElementTestPage.html")
        .getPath();
    webDriver.get("file://" + pathToHtmlFile);
  }

  @Test
  public void test() {
    for (int i = 0; i < 10000; i++) {
      logger.debug(Integer.toString(i));
      button.click();
    }
  }

  @Override
  public IFinder<?> getBrowserFinder() {
    return brFinder;
  }

}
