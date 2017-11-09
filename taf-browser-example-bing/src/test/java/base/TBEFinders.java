/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package base;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

/**
 * 
 */
public class TBEFinders {

  public static TBEBrFinder tafBrowserExampleWebElementFinder = null;

  private static String getPath(String filename) {
    System.out.println(filename);
    File f = new File(TBEFinders.class.getClassLoader().getResource(filename).getFile());
    return f.toString();
  }
  
  public static WebDriver getFirefox(int timeoutInSeconds) {
    System.setProperty("webdriver.gecko.driver", getPath("geckodriver.exe"));
    FirefoxProfile profile = new FirefoxProfile();
    profile.setEnableNativeEvents(true);
    WebDriver driver = new FirefoxDriver(profile);
    Options manager = driver.manage();
    manager.timeouts().implicitlyWait(new Double(timeoutInSeconds * 1000).longValue(), TimeUnit.MILLISECONDS);
    manager.window().maximize();
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
