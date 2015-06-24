/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package base;

import static org.junit.Assert.assertNotNull;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.baloise.testautomation.taf.browser.elements.BrFinder;

/**
 * 
 */
public class TBEBrFinder extends BrFinder {

  public TBEBrFinder(WebDriver driver) {
    super(driver);
  }

  public Object executeJavascript(String script) {
    JavascriptExecutor js = (JavascriptExecutor)driver;
    return js.executeScript(script);
  }

  public boolean isAjaxDone() {
    return true;
  }

  public void waitForAjaxDone(int seconds) {
    long time = System.currentTimeMillis();
    boolean done = false;
    while (!done) {
      done = isAjaxDone();
      if (System.currentTimeMillis() > time + 1000 * seconds) {
        done = true;
      }
      ;
    }
  }

  @Override
  public void waitUntilLoadingComplete() {
    assertNotNull("driver is NOT assigend --> no waitingUntilLoadingComplete possible", driver);
    waitForAjaxDone(20);
  }

}
