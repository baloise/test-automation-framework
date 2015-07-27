/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package tests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import base.TBEFinders;

import com.baloise.testautomation.taf.base.types.TafId;
import components.web.bing.Bing;

/**
 * 
 */
public class BingTest {

  @AfterClass
  public static void done() {
    TBEFinders.ResetFinders();
  }

  @BeforeClass
  public static void init() {
    WebDriver driver = TBEFinders.getFirefox(30);
    TBEFinders.InitFinders(driver);
    driver.get("http://www.bing.com");
  }

  private Bing bing;

  private void doBingTest(String id) {
    TafId.SetGlobalMandant("prod");
    bing = new Bing();
    bing.doSearch(id);
  }

  @Test
  public void searchGoogle() {
    doBingTest("google");
  }

  @Test
  public void searchStarbucks() {
    doBingTest("starbucks");
  }

  @Test
  public void searchSwiss() {
    doBingTest("swiss");
  }

}
