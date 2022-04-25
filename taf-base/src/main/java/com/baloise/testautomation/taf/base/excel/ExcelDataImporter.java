package com.baloise.testautomation.taf.base.excel;

import com.baloise.testautomation.taf.base._base.DataRow;
import com.baloise.testautomation.taf.base._base.TafAssert;
import com.baloise.testautomation.taf.base._interfaces.IDataImporter;
import com.baloise.testautomation.taf.base._interfaces.IDataRow;
import com.baloise.testautomation.taf.base._interfaces.IType;
import com.baloise.testautomation.taf.base.types.*;
import org.apache.poi.ss.formula.atp.AnalysisToolPak;
import org.apache.poi.ss.formula.functions.EDate;
import org.apache.poi.ss.formula.functions.EOMonth;
import org.apache.poi.ss.formula.functions.FreeRefFunction;
import org.apache.poi.ss.formula.udf.AggregatingUDFFinder;
import org.apache.poi.ss.formula.udf.DefaultUDFFinder;
import org.apache.poi.ss.formula.udf.UDFFinder;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import static com.baloise.testautomation.taf.base._base.TafAssert.*;

public class ExcelDataImporter implements IDataImporter {

  private static String mandantColName = "_mandant";
  private static String idColName = "_id";
  private static String detailColName = "_detail";
  private static String vpidColName = "_vpid";

  private Workbook workBook;
  private Sheet sheet;

  private Hashtable<String, Integer> columns = new Hashtable<String, Integer>();

  private String sheetName = null;
  private int sheetIndex = -1;

  private static UDFFinder finder;
  private static UDFFinder toolpack;

  {
    String[] functionNames = {"EDATUM", "MONATSENDE"};
    FreeRefFunction[] functionImpls = {EDate.instance, EndOfMonth.instance};
    finder = new DefaultUDFFinder(functionNames, functionImpls);
    toolpack = new AggregatingUDFFinder(finder);
  }

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
      FormulaEvaluator evaluator = workBook.getCreationHelper().createFormulaEvaluator();
      try {
        evaluator.evaluateInCell(cell);
      }
      catch (Exception e) {
        TafAssert.fail("Problems evaluation cell: " + cell.getCellFormula() + " -> " + e.getMessage());
      }
    }
    if (cell.getCellTypeEnum() == CellType.BLANK) {
      return TafString.emptyString();
    }
    if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
      return new TafBoolean(cell.getBooleanCellValue());
    }
    if (cell.getCellTypeEnum() == CellType.NUMERIC) {
      if (DateUtil.isCellDateFormatted(cell)) {
        return TafDate.normalDate(cell.getDateCellValue());
      }
      double value = cell.getNumericCellValue();
      if ((value == Math.floor(value)) && !Double.isInfinite(value)) {
        return new TafInteger(new Double(value).intValue());
      }
      return new TafDouble(cell.getNumericCellValue());
    }
    if (cell.getCellTypeEnum() == CellType.STRING) {
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
      Row row = sheet.getRow(rowIndex);
      if (isMatching(id, row)) {
        nrOfDataRows++;
      }
    }
    return nrOfDataRows;
  }

  private TafId getTafId(Row row) {
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
    String vpid = "";
    Integer colIndex = columns.get(vpidColName.toLowerCase());
    if (colIndex != null) {
      t = get(row.getCell(colIndex));
      if (t.isNotNull()) {
        vpid = t.asString().trim();
      }
    }
    if (vpid.isEmpty()) {
      return new TafId(mandant, id, detail);
    }
    else {
      return new TafId(mandant, id, detail, vpid);
    }
  }

  @Override
  public Collection<IDataRow> getWith(TafId id) {
    Vector<IDataRow> result = new Vector<>();
    for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
      Row row = sheet.getRow(rowIndex);
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
    Row header = sheet.getRow(0);
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
      workBook =  WorkbookFactory.create(is);
      workBook.addToolPack(toolpack);
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

  private boolean isMatching(TafId tafId, Row row) {
    return tafId.equals(getTafId(row));
  }

  private void registerFunctions() {
    try {
      AnalysisToolPak.registerFunction("EOMONTH", EOMonth.instance);
    }
    catch (Exception e) {}
  }

}
