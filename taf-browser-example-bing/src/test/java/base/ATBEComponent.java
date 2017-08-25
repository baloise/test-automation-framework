package base;

import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.baloise.testautomation.taf.base._base.ABase;
import com.baloise.testautomation.taf.base.types.TafId;

/**
 * 
 */
public abstract class ATBEComponent extends ABase {

  public static void assumeEnvironment(Class<?>... classes) {
    boolean canRun = false;
    for (Class<?> c : classes) {
      if (c.getSimpleName().equalsIgnoreCase(TafId.GetGlobalMandant())) {
        if (!canRun) {
          canRun = true;
        }
      }
    }
    assumeTrue("Umgebung passt nicht --> Testfall wird übersprungen", canRun);
  }

  public void clickText(String text) {
    getBrowserFinder().getDriver().findElement(By.linkText(text)).click();
  }

  public void clickText(String text, int index) {
    try {
      getBrowserFinder().getDriver().findElements(By.linkText(text)).get(index).click();
    }
    catch (Exception e) {
      fail("Element mit index = " + index + " nicht gefunden/geklickt");
    }
  }

  // @Override
  public WebElement find() {
    return getBrowserFinder().find(by);
  }

  @Override
  public TBEBrFinder getBrowserFinder() {
    return TBEFinders.tafBrowserExampleWebElementFinder;
  }

  public void hoverTo(String text) {
    WebElement we = getBrowserFinder().getDriver().findElement(By.linkText(text));
    new Actions(getBrowserFinder().getDriver()).moveToElement(we).perform();
  }

}
