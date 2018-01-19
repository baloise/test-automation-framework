package com.baloise.testautomation.taf.swing.client.proxies;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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
