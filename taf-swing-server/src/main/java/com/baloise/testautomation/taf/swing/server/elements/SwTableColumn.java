package com.baloise.testautomation.taf.swing.server.elements;

import java.awt.Component;

import javax.swing.JTable;

import org.assertj.swing.fixture.JTableHeaderFixture;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTableColumn;

public class SwTableColumn extends ASwElement implements ISwTableColumn<Component> {

  public SwTableColumn(long tid, int column, JTable c) {
    super(tid, c);
  }

  @Override
  public TafProperties basicExecCommand(TafProperties props) {
    throw new NotSupportedException("no commands implemented");
  }

  @Override
  public void fillProperties() {}

  @Override
  public JTable getComponent() {
    return (JTable)component;
  }

  @Override
  public JTableHeaderFixture getFixture() {
    return new JTableHeaderFixture(getRobot(), getComponent().getTableHeader());
  }

  @Override
  public String getType() {
    return ISwTableColumn.type;
  }

}
