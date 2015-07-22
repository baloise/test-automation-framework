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

import org.assertj.swing.data.TableCell;
import org.assertj.swing.fixture.JTableFixture;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTable;

/**
 * 
 */
public class SwTable extends ASwElement implements ISwTable<Component> {

  public SwTable(long tid, JTable c) {
    super(tid, c);
  }

  @Override
  public void fillProperties() {}

  @Override
  public JTable getComponent() {
    return (JTable)component;
  }

  @Override
  public String getType() {
    return ISwTable.type;
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
  public TafProperties basicExecCommand(TafProperties props) {
    Command c = getCommand(Command.class, props.getString(paramCommand));
    switch (c) {
      case clickcell:
        basicClickCell(props);
        break;
      default:
        throw new IllegalArgumentException("command not implemented: " + c);
    }
    return props;
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

  @Override
  public JTableFixture getFixture() {
    return new JTableFixture(getRobot(), getComponent());
  }

}
