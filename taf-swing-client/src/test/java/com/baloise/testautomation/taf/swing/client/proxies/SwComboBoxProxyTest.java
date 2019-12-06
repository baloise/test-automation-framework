package com.baloise.testautomation.taf.swing.client.proxies;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SwComboBoxProxyTest {

  @Test
  public void testAllItemsRegexpSplit() {
    String itemsAsString = "|männlich|weiblich";
    String[] splittedItems = new SwComboBoxProxy().getSplitedItems(itemsAsString);
    assertEquals(3, splittedItems.length);
    assertEquals("", splittedItems[0]);
    assertEquals("männlich", splittedItems[1]);
    assertEquals("weiblich", splittedItems[2]);
  }

}
