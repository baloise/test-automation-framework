package com.baloise.testautomation.taf.browser.elements;

import com.baloise.testautomation.taf.base._interfaces.IAnnotations;
import com.baloise.testautomation.taf.browser.TafBrowserTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BrComboboxTest extends TafBrowserTest {

  @IAnnotations.ById("Combobox")
  private BrCombobox combobox;

  @Test
  public void test() {
    assertEquals("A",
                 combobox.get()
                         .asString());
    combobox.fillWith("B");
    assertEquals("B",
                 combobox.get()
                         .asString());
    combobox.fillWith("A");
    assertEquals("A",
                 combobox.get()
                         .asString());
  }

}