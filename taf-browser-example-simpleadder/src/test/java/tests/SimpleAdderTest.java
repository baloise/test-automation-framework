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
import eclipse.jubula.auts.SimpleAdder;

import com.baloise.testautomation.taf.base.types.TafId;

/**
 * Example Tests for AUT: SimpleAdder
 */
public class SimpleAdderTest {
  @AfterClass
  public static void done() {
    TBEFinders.ResetFinders();
  }

  @BeforeClass
  public static void init() {
    WebDriver driver = TBEFinders.getFirefox(30);
    TBEFinders.InitFinders(driver);
    driver.get("https://www.eclipse.org/jubula/example/auts/adder/SimpleAdder.html");
  }

  private SimpleAdder simpleAdder;

  private void doSimpleAdderTest(String id) {
    TafId.SetGlobalMandant("prod");
    simpleAdder = new SimpleAdder();
    simpleAdder.doAddition(id);
  }

  @Test
  public void searchScenario1() {
    doSimpleAdderTest("scenario1");
  }

  @Test
  public void searchScenario2() {
    doSimpleAdderTest("scenario2");
  }

  @Test
  public void searchScenario3() {
    doSimpleAdderTest("scenario3");
  }
}