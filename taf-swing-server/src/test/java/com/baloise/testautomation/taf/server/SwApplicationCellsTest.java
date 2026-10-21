package com.baloise.testautomation.taf.server;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Hashtable;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.baloise.testautomation.taf.swing.server.main.SwApplication;

public class SwApplicationCellsTest {

  private final PrintStream originalOut = System.out;
  private final ByteArrayOutputStream out = new ByteArrayOutputStream();

  @Before
  public void captureOut() {
    System.setOut(new PrintStream(out, true));
  }

  @After
  public void restore() {
    System.setOut(originalOut);
    System.clearProperty(SwApplication.DEBUG_PROPERTY);
  }

  private String cellsToXml() {
    JTable table = new JTable(new DefaultTableModel(new Object[][] {{"a", null}}, new Object[] {"A", "B"}));
    SwApplication app = new SwApplication();
    app.components = new Hashtable<>();
    StringBuilder xml = new StringBuilder();
    app.allCellsToXML(xml, table);
    return xml.toString();
  }

  @Test
  public void emptyCellIsNotLoggedByDefault() {
    String xml = cellsToXml();
    Assert.assertFalse(out.toString().contains("cellText is null"));
    Assert.assertTrue(xml.contains("row = \"0\" col = \"1\" value = \"\""));
  }

  @Test
  public void emptyCellIsLoggedInDebugMode() {
    System.setProperty(SwApplication.DEBUG_PROPERTY, "true");
    cellsToXml();
    Assert.assertTrue(out.toString().contains("cellText is null: row = 0, column = 1"));
  }
}
