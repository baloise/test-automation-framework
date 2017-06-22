package com.baloise.testautomation.taf.swing.base.tabledata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.baloise.testautomation.taf.common.interfaces.ITableData;
import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTable;

public class SwTableData implements ITableData {

  private int nrOfRows = 0;
  private int nrOfCols = 0;
  private Map<Integer, String> headers = new Hashtable<Integer, String>();
  private Map<Integer, String[]> data = new Hashtable<Integer, String[]>();

  public SwTableData(TafProperties props) {
    try {
      nrOfRows = props.getLong(ISwTable.paramNrOfRows).intValue();
    }
    catch (Exception e) {}
    try {
      nrOfCols = props.getLong(ISwTable.paramNrOfCols).intValue();
    }
    catch (Exception e) {}
    for (int col = 0; col < nrOfCols; col++) {
      headers.put(col, props.getString("header:" + col));
    }
    for (int row = 0; row < nrOfRows; row++) {
      String[] rowData = new String[nrOfCols];
      data.put(row, rowData);
      for (int col = 0; col < nrOfCols; col++) {
        rowData[col] = props.getString(row + ":" + col);
      }
    }
  }

  @Override
  public int getNrOfColumns() {
    return nrOfCols;
  }

  @Override
  public int getNrOfRows() {
    return nrOfRows;
  }

  @Override
  public String getHeader(int col) {
    return headers.get(col);
  }

  @Override
  public Integer getColumnIndex(String header) {
    if (header == null) {
      return null;
    }
    for (int i = 0; i < headers.size(); i++) {
      if (header.equalsIgnoreCase(headers.get(i))) {
        return i;
      }
    }
    return null;
  }

  @Override
  public String get(int row, int col) {
    try {
      return data.get(row)[col];
    }
    catch (Exception e) {}
    return null;
  }

  @Override
  public String get(int row, String colHeader) {
    return get(row, getColumnIndex(colHeader));
  }

  @Override
  public List<String> getHeaders() {
    List<String> result = new ArrayList<String>();
    for (int i = 0; i < headers.size(); i++) {
      result.add(headers.get(i));
    }
    return result;
  }

  @Override
  public List<String> getRow(int row) {
    return Arrays.asList(data.get(row));
  }

  @Override
  public List<String> getColumn(int col) {
    List<String> result = new ArrayList<String>();
    for (int row = 0; row < getNrOfRows(); row++) {
      try {
        result.add(data.get(row)[col]);
      }
      catch (Exception e) {
        result.add(null);
      }
    }
    return result;
  }

  @Override
  public List<String> getColumn(String colHeader) {
    return getColumn(getColumnIndex(colHeader));
  }

  public boolean checkCellExists(String celltext, int count) {
    int nrOfMatches = 0;
    for (int row = 0; row < getNrOfRows(); row++) {
      List<String> rowContents = getRow(row);
      for (String cellContents : rowContents) {
        if (celltext.equals(cellContents)) {
          nrOfMatches++;
        }
      }
    }
    return count == nrOfMatches;
  }
  
  public boolean checkCellExists(String celltext) {
    return checkCellExists(celltext, 1);
  }
  
}
