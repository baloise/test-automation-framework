/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.base.csv;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.baloise.testautomation.taf.base._base.DataRow;
import com.baloise.testautomation.taf.base._interfaces.IDataImporter;
import com.baloise.testautomation.taf.base._interfaces.IDataRow;
import com.baloise.testautomation.taf.base.types.TafId;
import com.baloise.testautomation.taf.base.types.TafString;

/**
 * 
 */
public class CsvDataImporter implements IDataImporter {

  private static String mandantColName = "_mandant";
  private static String idColName = "_id";
  private static String detailColName = "_detail";
  private static String vpidColName = "_vpid";

  private List<CSVRecord> records;
  private Map<String, Integer> headerMap = new Hashtable<String, Integer>();

  public CsvDataImporter(File f) {
    assertNotNull("input stream may not be null", f);
    initWith(f);
  }

  private void initWith(File f) {
    records = null;
    FileReader fr = null;
    try {
      fr = new FileReader(f);
      CSVParser parser = CSVFormat.EXCEL.withDelimiter(';').parse(fr);
      records = parser.getRecords();
      CSVRecord headers = records.get(0);
      records.remove(0);
      for (int i = 0; i < headers.size(); i++) {
        headerMap.put(headers.get(i).toLowerCase(), i);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      if (fr != null) {
        try {
          fr.close();
        }
        catch (IOException e1) {}
      }
    }
  }

  @Override
  public int getNrOfDataRowsWith(TafId id) {
    assertNotNull("no CSV records found", records);
    return getWith(id).size();
  }

  @Override
  public Collection<IDataRow> getWith(TafId id) {
    assertNotNull("no CSV records found", records);
    List<CSVRecord> matchingRecords = new ArrayList<CSVRecord>();

    for (CSVRecord record : records) {
      if (isMatching(record, id)) {
        matchingRecords.add(record);
      }
    }

    assertNotNull("no matching record found", matchingRecords);
    return convertToDataRow(matchingRecords);
  }

  private boolean isMatching(CSVRecord record, TafId id) {
    assertNotNull("record may not be null", record);
    TafId recordId = getRecordId(record);
    return recordId.equals(id);
  }

  private TafId getRecordId(CSVRecord record) {
    String vpid = null;
    try {
      vpid = record.get(headerMap.get(vpidColName));
    }
    catch (Exception e) {}
    TafId recordId = new TafId(record.get(headerMap.get(mandantColName)), record.get(headerMap.get(idColName)),
        record.get(headerMap.get(detailColName)), vpid);
    return recordId;
  }

  private Collection<IDataRow> convertToDataRow(List<CSVRecord> records) {
    List<IDataRow> data = new ArrayList<IDataRow>();
    Map<String, Integer> columns = new Hashtable<>(headerMap);
    columns.remove(mandantColName);
    columns.remove(idColName);
    columns.remove(detailColName);
    columns.remove(vpidColName);
    for (CSVRecord record : records) {
      TafId id = getRecordId(record); 
      DataRow dataRow = new DataRow();
      dataRow.setId(id);
      for (String key : columns.keySet()) {
        dataRow.set(key, TafString.normalString(record.get(columns.get(key))));
      }
      data.add(dataRow);
    }
    return data;
  }

}
