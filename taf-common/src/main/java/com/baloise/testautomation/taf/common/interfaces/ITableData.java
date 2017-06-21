package com.baloise.testautomation.taf.common.interfaces;

import java.util.List;

public interface ITableData {
  
  public int getNrOfColumns();
  public int getNrOfRows();
  
  public String getHeader(int col);
  public Integer getColumnIndex(String header);
  
  public String get(int row, int col);
  public String get(int row, String colHeader);

  public List<String> getHeaders();
  public List<String> getRow(int row);
  public List<String> getColumn(int col);
  
  public List<String> getColumn(String colHeader);
  
}
