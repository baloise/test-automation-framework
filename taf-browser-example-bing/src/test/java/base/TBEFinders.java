/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package base;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

/**
 * 
 */
public class TBEFinders {

  public static TBEBrFinder tafBrowserExampleWebElementFinder = null;

  public static WebDriver getFirefox(int timeoutInSeconds) {
    FirefoxProfile profile = new FirefoxProfile();
    profile.setEnableNativeEvents(true);
    WebDriver driver = new FirefoxDriver(profile);
    driver.manage().timeouts().implicitlyWait(new Double(timeoutInSeconds * 1000).longValue(), TimeUnit.MILLISECONDS);
    driver.manage().window().maximize();
    return driver;
  }

  public static void InitFinders(WebDriver driver) {
    tafBrowserExampleWebElementFinder = new TBEBrFinder(driver);
  }

  public static void ResetFinders() {
    if (tafBrowserExampleWebElementFinder != null) {
      tafBrowserExampleWebElementFinder.getDriver().quit();
    }
    tafBrowserExampleWebElementFinder = null;
  }
}
