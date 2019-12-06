package com.baloise.testautomation.taf.browser.elements;

import com.baloise.testautomation.taf.base._interfaces.IAnnotations;
import com.baloise.testautomation.taf.base.types.TafDate;
import com.baloise.testautomation.taf.browser.TafBrowserTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BrDateInputTest extends TafBrowserTest {

  @IAnnotations.ById("DateInput")
  private BrDateInput dateInput;

  @Test
  public void test() {
    assertNull(dateInput.get()
                        .asDate());
    TafDate expectedDate = new TafDate("1.2.1973");
    dateInput.fillWith(expectedDate.asString());
    assertEquals(expectedDate.asDate(),
                 dateInput.get()
                          .asDate());
  }

}