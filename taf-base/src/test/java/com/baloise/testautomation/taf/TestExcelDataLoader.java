/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Vector;

import org.junit.Test;

import com.baloise.testautomation.taf.base._interfaces.IDataRow;
import com.baloise.testautomation.taf.base.excel.ExcelDataImporter;
import com.baloise.testautomation.taf.base.types.TafBoolean;
import com.baloise.testautomation.taf.base.types.TafDate;
import com.baloise.testautomation.taf.base.types.TafDouble;
import com.baloise.testautomation.taf.base.types.TafId;
import com.baloise.testautomation.taf.base.types.TafInteger;
import com.baloise.testautomation.taf.base.types.TafString;

/**
 * 
 */
public class TestExcelDataLoader {

  @Test
  public void nrOfRows() {
    ExcelDataImporter edl = new ExcelDataImporter(
        new File(getClass().getResource("TestExcelDataLoader.xls").getFile()), 0);
    assertEquals(1, edl.getNrOfDataRowsWith(new TafId("int", "test", "1")));
    assertEquals(1, edl.getNrOfDataRowsWith(new TafId("int", "TEST", "2")));
    assertEquals(0, edl.getNrOfDataRowsWith(new TafId("inti", "TEST", "2")));
  }

  @Test
  public void openWithFile() {
    ExcelDataImporter edl = new ExcelDataImporter(
        new File(getClass().getResource("TestExcelDataLoader.xls").getFile()), 0);
    assertEquals(1, edl.getNrOfDataRowsWith(new TafId("int", "test", "1")));
  }

  @Test
  public void openWithRessource() {
    try (InputStream is = getClass().getResource("TestExcelDataLoader.xls").openStream()) {
      ExcelDataImporter edl = new ExcelDataImporter(is, "data");
      assertEquals(1, edl.getNrOfDataRowsWith(new TafId("int", "test", "1")));
    }
    catch (Exception e) {
      fail("error opening file");
    }

  }

  @Test
  public void rowContents1() {
    ExcelDataImporter edl = new ExcelDataImporter(
        new File(getClass().getResource("TestExcelDataLoader.xls").getFile()), 0);
    Collection<IDataRow> data = edl.getWith(new TafId("int", "TEst", "1"));
    assertEquals(1, data.size());
    Vector<IDataRow> vData = new Vector<>();
    vData.addAll(data);
    assertEquals(TafInteger.class, vData.get(0).get("INTEGER").getClass());
    assertEquals(TafDouble.class, vData.get(0).get("double").getClass());
    assertEquals(TafDate.class, vData.get(0).get("date").getClass());
    assertEquals(TafString.class, vData.get(0).get("string").getClass());
    assertEquals(TafBoolean.class, vData.get(0).get("boolean").getClass());
  }

  @Test
  public void rowContents2() {
    ExcelDataImporter edl = new ExcelDataImporter(
        new File(getClass().getResource("TestExcelDataLoader.xls").getFile()), 0);
    Collection<IDataRow> data = edl.getWith(new TafId("int", "TEst", "2"));
    assertEquals(1, data.size());
    Vector<IDataRow> vData = new Vector<>();
    vData.addAll(data);
    assertEquals(2, vData.get(0).get("INTEGER").asInteger().intValue());
    assertEquals(new Double(2.2), vData.get(0).get("Double").asDouble());
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    try {
      assertEquals(sdf.parse("02.02.2000"), vData.get(0).get("date").asDate());
    }
    catch (ParseException e) {
      fail();
    }
    assertEquals("Zwei String", vData.get(0).get("string").asString());
    assertEquals(false, vData.get(0).get("boolean").asBoolean());
  }

  @Test
  public void rowContents3() {
    ExcelDataImporter edl = new ExcelDataImporter(
        new File(getClass().getResource("TestExcelDataLoader.xls").getFile()), 0);
    Collection<IDataRow> data = edl.getWith(new TafId("test", "othertest", ""));
    assertEquals(1, data.size());
    Vector<IDataRow> vData = new Vector<>();
    vData.addAll(data);
    assertTrue(vData.get(0).get("INTEGER").isNull());
    assertTrue(vData.get(0).get("double").isSkip());
    assertTrue(vData.get(0).get("date").isEmpty());
    assertEquals(TafString.class, vData.get(0).get("string").getClass());
    assertEquals(TafDouble.class, vData.get(0).get("boolean").getClass());

  }

  @Test
  public void rowContents4() {
    ExcelDataImporter edl = new ExcelDataImporter(
        new File(getClass().getResource("TestExcelDataLoader.xls").getFile()), 0);
    Collection<IDataRow> data = edl.getWith(new TafId("test", "string", ""));
    assertEquals(1, data.size());
    Vector<IDataRow> vData = new Vector<>();
    vData.addAll(data);
    assertTrue(vData.get(0).get("INTEGER") instanceof TafString);
    assertTrue(vData.get(0).get("double") instanceof TafString);

  }

  @Test
  public void rowTafId() {
    ExcelDataImporter edl = new ExcelDataImporter(
        new File(getClass().getResource("TestExcelDataLoader.xls").getFile()), 0);
    Collection<IDataRow> data = edl.getWith(new TafId("int", "TEst", ""));
    assertEquals(2, data.size());
    Vector<IDataRow> vData = new Vector<>();
    vData.addAll(data);
    assertTrue(vData.get(0).getId().asIdDetailString().equalsIgnoreCase("test-1"));
    assertTrue(vData.get(1).getId().asIdDetailString().equalsIgnoreCase("test-2"));
  }
}
