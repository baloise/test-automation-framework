package com.baloise.testautomation.taf.swing.server.elements;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.junit.Test;

public class SwComboBoxTest {

  @Test
  public void testSerializeItems() {
    List<String> allItems = new ArrayList<String>();
    allItems.add("");
    allItems.add("männlich");
    allItems.add("weiblich");
    JComboBox component = new JComboBox();
    JInternalFrame parent = new JInternalFrame();
    parent.add(component);
    component.setName("MyComboBox");
    String items = new SwComboBox(100, component).serializeItems(allItems);
    assertEquals("|männlich|weiblich", items);
  }
}
