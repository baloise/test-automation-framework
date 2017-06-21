/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.client.proxies;

import com.baloise.testautomation.taf.common.interfaces.ITableData;
import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTable;
import com.baloise.testautomation.taf.swing.base.tabledata.SwTableData;

/**
 * 
 */
public class SwTableProxy extends ASwElementProxy implements ISwTable<Long> {

  @Override
  public void clickCell(int row, int col) {
    executeCommand(Command.clickcell.toString(), getProperties(row, col));
  }

  @Override
  public void clickCell(String text) {
    executeCommand(Command.clickcell.toString(), getProperties(text));
  }

  @Override
  public void clickHeader(String columnName) {
    executeCommand(Command.clickheader.toString(), getProperties(columnName));
  }

  @Override
  public void doubleClickCell(int row, int col) {
    executeCommand(Command.doubleclickcell.toString(), getProperties(row, col));
  }

  @Override
  public void doubleClickCell(String text) {
    executeCommand(Command.doubleclickcell.toString(), getProperties(text));
  }

  @Override
  public String getCellText(int row, int col) {
    TafProperties props = executeCommand(Command.getcelltext.toString(), getProperties(row, col));
    return props.getString(paramText);
  }

  private TafProperties getProperties(int row, int col) {
    TafProperties props = new TafProperties();
    props.putObject(paramRow, row);
    props.putObject(paramCol, col);
    return props;
  }

  private TafProperties getProperties(String text) {
    TafProperties props = new TafProperties();
    props.putObject(paramText, text);
    return props;
  }

  @Override
  public String getType() {
    return ISwTable.type;
  }

  @Override
  public void rightClickCell(int row, int col) {
    executeCommand(Command.rightclickcell.toString(), getProperties(row, col));
  }

  @Override
  public void rightClickCell(String text) {
    executeCommand(Command.rightclickcell.toString(), getProperties(text));
  }

  @Override
  public Long getCellRow(String value) {
    TafProperties inputProps = new TafProperties();
    inputProps.putObject(paramText, value);
    TafProperties outputProps = executeCommand(Command.getcellindex.toString(), inputProps);
    return outputProps.getLong(paramText);
  }

  @Override
  public void enterValue(int row, int column, String value) {
    TafProperties props = new TafProperties();
    props.putObject(paramRow, row);
    props.putObject(paramCol, column);
    props.putObject(paramText, value);
    executeCommand(Command.entervalue.toString(), props);
  }

  @Override
  public boolean cellExists(String text) {
    TafProperties outputProps = executeCommand(Command.cellexists.toString(), getProperties(text));
    return outputProps.getBoolean(paramCellExists);
  }

  @Override
  public ITableData getData() {
    TafProperties props = executeCommand(Command.getdata.toString());
    return new SwTableData(props);
  }

}
