/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.server.elements;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import org.assertj.swing.data.TableCell;
import org.assertj.swing.exception.ActionFailedException;
import org.assertj.swing.fixture.JTableFixture;
import org.assertj.swing.fixture.JTableHeaderFixture;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTable;

/**
 * 
 */
public class SwTable extends ASwElement implements ISwTable<Component> {

  public SwTable(long tid, JTable c) {
    super(tid, c);
  }

  public void basicClickCell(TafProperties props) {
    if (props.getString(paramText) != null) {
      clickCell(props.getString(paramText));
      props.clear();
      return;
    }
    if (props.getLong(paramRow) >= 0) {
      clickCell(props.getLong(paramRow).intValue(), props.getLong(paramCol).intValue());
      props.clear();
      return;
    }
    throw new NotSupportedException("clickCell contains unknown parameters");
  }

  public void basicDoubleClickCell(TafProperties props) {
    if (props.getString(paramText) != null) {
      doubleClickCell(props.getString(paramText));
      props.clear();
      return;
    }
    if (props.getLong(paramRow) >= 0) {
      doubleClickCell(props.getLong(paramRow).intValue(), props.getLong(paramCol).intValue());
      props.clear();
      return;
    }
    throw new NotSupportedException("doubleClickCell contains unknown parameters");
  }

  @Override
  public TafProperties basicExecCommand(TafProperties props) {
    Command c = getCommand(Command.class, props.getString(paramCommand));
    switch (c) {
      case clickcell:
        basicClickCell(props);
        break;
      case clickheader:
        String columnName = props.getString(paramText);
        props.clear();
        clickHeader(columnName);
        break;
      case rightclickcell:
        basicRightClickCell(props);
        break;
      case doubleclickcell:
        basicDoubleClickCell(props);
        break;
      case getcelltext:
        String text = getCellText(props.getLong(paramRow).intValue(), props.getLong(paramCol).intValue());
        props.clear();
        props.putObject(paramText, text);
        break;
      case getcellindex:
        String celltext = props.getString(paramText);
        props.clear();
        props.putObject(paramText, getCellRow(celltext));
        break;
      case entervalue:
        String value = props.getString(paramText);
        int row = props.getLong(paramRow).intValue();
        int column = props.getLong(paramCol).intValue();
        props.clear();
        enterValue(row, column, value);
        break;
      case cellexists:
        String cellValue = props.getString(paramText);
        props.clear();
        props.putObject(paramCellExists, cellExists(cellValue));
        break;
      default:
        throw new IllegalArgumentException("command not implemented: " + c);
    }
    return props;
  }

  @Override
  public void enterValue(int row, int column, String value) {
    getFixture().enterValue(TableCell.row(row).column(column), value);
  }

  public void basicRightClickCell(TafProperties props) {
    if (props.getString(paramText) != null) {
      rightClickCell(props.getString(paramText));
      props.clear();
      return;
    }
    if (props.getLong(paramRow) >= 0) {
      rightClickCell(props.getLong(paramRow).intValue(), props.getLong(paramCol).intValue());
      props.clear();
      return;
    }
    throw new NotSupportedException("rightClickCell contains unknown parameters");
  }

  @Override
  public void clickCell(int row, int col) {
    TableCell cell = TableCell.row(row).column(col);
    getFixture().cell(cell).click();
  }

  @Override
  public void clickCell(String text) {
    getFixture().cell(text).click();
  }

  @Override
  public void clickHeader(String columnName) {
    JTableHeaderFixture tableHeader = getFixture().tableHeader();
    JTableHeader target = tableHeader.target();
    TableColumnModel columnModel = target.getColumnModel();
    int index = -1;
    for (int i = 0; i < columnModel.getColumnCount(); i++) {
      if (columnName.equals(columnModel.getColumn(i).getHeaderValue())) {
        index = i;
        break;
      }
    }
    if (index >= 0) {
      getFixture().tableHeader().clickColumn(index);
    }
    else {
      System.out.println("Table column with name '" + columnName + "' --> NOT found --> NOT clicked");
    }
  }

  @Override
  public boolean cellExists(String text) {
    try {
      getFixture().cell(text);
      return true;
    }
    catch (ActionFailedException e) {
      return false;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doubleClickCell(int row, int col) {
    TableCell cell = TableCell.row(row).column(col);
    getFixture().cell(cell).doubleClick();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doubleClickCell(String text) {
    getFixture().cell(text).doubleClick();
  }

  @Override
  public void fillProperties() {}

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCellText(int row, int col) {
    return getFixture().valueAt(TableCell.row(row).column(col));
  }

  @Override
  public JTable getComponent() {
    return (JTable)component;
  }

  @Override
  public JTableFixture getFixture() {
    return new JTableFixture(getRobot(), getComponent());
  }

  @Override
  public String getType() {
    return ISwTable.type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void rightClickCell(int row, int col) {
    TableCell cell = TableCell.row(row).column(col);
    getFixture().cell(cell).rightClick();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void rightClickCell(String text) {
    getFixture().cell(text).rightClick();
  }

  @Override
  public Long getCellRow(String value) {
    return (long)getFixture().cell(value).row();
  }

}
