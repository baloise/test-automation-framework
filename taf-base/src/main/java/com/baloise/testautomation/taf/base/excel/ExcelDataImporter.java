/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.base.excel;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.atp.AnalysisToolPak;
import org.apache.poi.ss.formula.functions.FreeRefFunction;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

import com.baloise.testautomation.taf.base._base.DataRow;
import com.baloise.testautomation.taf.base._interfaces.IDataImporter;
import com.baloise.testautomation.taf.base._interfaces.IDataRow;
import com.baloise.testautomation.taf.base._interfaces.IType;
import com.baloise.testautomation.taf.base.types.TafBoolean;
import com.baloise.testautomation.taf.base.types.TafDate;
import com.baloise.testautomation.taf.base.types.TafDouble;
import com.baloise.testautomation.taf.base.types.TafId;
import com.baloise.testautomation.taf.base.types.TafInteger;
import com.baloise.testautomation.taf.base.types.TafString;

/**
 * 
 */
public class ExcelDataImporter implements IDataImporter {

  private static String mandantColName = "_mandant";
  private static String idColName = "_id";
  private static String detailColName = "_detail";
  private static String vpidColName = "_vpid";

  private HSSFWorkbook workBook;
  private HSSFSheet sheet;

  private Hashtable<String, Integer> columns = new Hashtable<String, Integer>();

  private String sheetName = null;
  private int sheetIndex = -1;

  public ExcelDataImporter(File f, int sheetIndex) {
    this.sheetIndex = sheetIndex;
    initWith(f);
  }

  public ExcelDataImporter(File f, String sheetName) {
    this.sheetName = sheetName;
    initWith(f);
  }

  public ExcelDataImporter(InputStream inputStream, int sheetIndex) {
    assertNotNull("input stream may not be null", inputStream);
    this.sheetIndex = sheetIndex;
    initWith(inputStream);
  }

  public ExcelDataImporter(InputStream inputStream, String sheetName) {
    assertNotNull("input stream may not be null", inputStream);
    this.sheetName = sheetName;
    initWith(inputStream);
  }

  private IType get(Cell cell) {
    if (cell == null) {
      return TafString.nullString();
    }
    if (cell.getCellTypeEnum() == CellType.FORMULA) {
      String cellFormula = cell.getCellFormula();
      cellFormula = cellFormula.replace("EDATUM", "EDATE");
      cell.setCellFormula(cellFormula);
      FormulaEvaluator evaluator = workBook.getCreationHelper().createFormulaEvaluator();
      evaluator.evaluateInCell(cell);
    }
    if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
      return TafString.emptyString();
    }
    if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
      return new TafBoolean(cell.getBooleanCellValue());
    }
    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
      if (DateUtil.isCellDateFormatted(cell)) {
        return TafDate.normalDate(cell.getDateCellValue());
      }
      double value = cell.getNumericCellValue();
      if ((value == Math.floor(value)) && !Double.isInfinite(value)) {
        return new TafInteger(new Double(value).intValue());
      }
      return new TafDouble(cell.getNumericCellValue());
    }
    if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
      String s = cell.getStringCellValue();
      if (TafBoolean.TRUE.equalsIgnoreCase(s)) {
        return TafBoolean.trueBoolean();
      }
      if (TafBoolean.FALSE.equalsIgnoreCase(s)) {
        return TafBoolean.falseBoolean();
      }
      return TafString.normalString(s);
    }
    return TafString.nullString();
  }

  @Override
  public int getNrOfDataRowsWith(TafId id) {
    int nrOfDataRows = 0;
    for (int rowIndex = 1; rowIndex < sheet.getLastRowNum(); rowIndex++) {
      HSSFRow row = sheet.getRow(rowIndex);
      if (isMatching(id, row)) {
        nrOfDataRows++;
      }
    }
    return nrOfDataRows;
  }

  private TafId getTafId(HSSFRow row) {
    if (row == null) {
      return new TafId("");
    }
    IType t;
    String mandant = "";
    t = get(row.getCell(columns.get(mandantColName.toLowerCase())));
    if (t.isNotNull()) {
      mandant = t.asString();
    }
    String id = "";
    t = get(row.getCell(columns.get(idColName.toLowerCase())));
    if (t.isNotNull()) {
      id = t.asString();
    }
    String detail = "";
    t = get(row.getCell(columns.get(detailColName.toLowerCase())));
    if (t.isNotNull()) {
      detail = t.asString();
    }
    return new TafId(mandant, id, detail);
  }

  @Override
  public Collection<IDataRow> getWith(TafId id) {
    Vector<IDataRow> result = new Vector<>();
    for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
      HSSFRow row = sheet.getRow(rowIndex);
      if (isMatching(id, row)) {
        TafId tafId = getTafId(row);
        DataRow dataRow = new DataRow();
        dataRow.setId(tafId);
        Iterator<String> colNames = columns.keySet().iterator();
        while (colNames.hasNext()) {
          String colName = colNames.next();
          Cell c = row.getCell(columns.get(colName));
          dataRow.set(colName, get(c));
        }
        result.add(dataRow);
      }
    }
    return result;
  }

  private void initColumns() {
    HSSFRow header = sheet.getRow(0);
    Iterator<Cell> cells = header.cellIterator();
    while (cells.hasNext()) {
      Cell cell = cells.next();
      if (cell != null) {
        columns.put(cell.getStringCellValue().toLowerCase(), cell.getColumnIndex());
      }
    }
    assertTrue("column named '_mandant' is missing", columns.containsKey(mandantColName.toLowerCase()));
    assertTrue("column named '_id' is missing", columns.containsKey(idColName.toLowerCase()));
    assertTrue("column named '_detail' is missing", columns.containsKey(detailColName.toLowerCase()));
  }

  private void initSheet(InputStream is) {
    try {
      workBook = new HSSFWorkbook(is);
      if (sheetName != null) {
        sheet = workBook.getSheet(sheetName);
        return;
      }
      if (sheetIndex >= 0) {
        sheet = workBook.getSheetAt(sheetIndex);
        return;
      }
      fail("sheet not set because neither index or name are valid");
    }
    catch (Exception e) {
      fail("error opening excel file with sheet = " + sheetName);
    }
  }

  private void initWith(File f) {
    assertTrue("file does NOT exist: " + f.getAbsolutePath(), f.exists());
    try {
      InputStream inputStream = new FileInputStream(f);
      initWith(inputStream);
      inputStream.close();
    }
    catch (Exception e) {
      fail("error opening file: " + f);
    }
  }

  private void initWith(InputStream is) {
    registerFunctions();
    initSheet(is);
    initColumns();
  }

  private boolean isMatching(TafId tafId, HSSFRow row) {
    return tafId.equals(getTafId(row));
  }

  private void registerFunctions() {
    try {
      FreeRefFunction EOMONTH = new EndOfMonth();
      AnalysisToolPak.registerFunction("EOMONTH", EOMONTH);
    }
    catch (Exception e) {}
  }

}
