/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.base._interfaces;

import com.baloise.testautomation.taf.common.interfaces.ISwElement;
import com.baloise.testautomation.taf.common.interfaces.ITableData;

/**
 * 
 */
public interface ISwTable<R> extends ISwElement<R> {

  public enum Command {
    clickcell, clickheader, doubleclickcell, rightclickcell, getcelltext, getcellindex, entervalue, cellexists, getdata,
    selectrows
  }

  public final String paramText = "text";

  public final String paramRow = "row";

  public final String paramCol = "col";

  public final String paramNrOfRows = "nrOfRows";

  public final String paramNrOfCols = "nrOfCols";
  
  public final String paramCellExists = "cellExists";

  public final String paramSelectRows = "selectRows";

  public final String type = "table";

  public void clickCell(int row, int col);

  public void clickCell(String text);

  public void clickHeader(String columnName);
  
  public void doubleClickCell(int row, int col);

  public void doubleClickCell(String text);

  public String getCellText(int row, int col);

  public void rightClickCell(int row, int col);

  public void rightClickCell(String text);

  public Long getCellRow(String value);

  public void enterValue(int row, int column, String value);

  public void selectRows(int... rows);

  public boolean cellExists(String text);

  public ITableData getData();
  
}
