package com.baloise.testautomation.taf.browser.elements;

import com.baloise.testautomation.taf.base._interfaces.IAnnotations;
import com.baloise.testautomation.taf.browser.TafBrowserTest;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BrCheckboxTest extends TafBrowserTest {

  @IAnnotations.ById("Checkbox")
  private BrCheckbox checkbox;

  @Test
  public void test() {
    checkbox.select();
    assertTrue(checkbox.get().asBoolean());
    checkbox.unselect();
    assertFalse(checkbox.get().asBoolean());
    checkbox.select(true);
    assertTrue(checkbox.get().asBoolean());
    checkbox.select(false);
    assertFalse(checkbox.get().asBoolean());
  }

}