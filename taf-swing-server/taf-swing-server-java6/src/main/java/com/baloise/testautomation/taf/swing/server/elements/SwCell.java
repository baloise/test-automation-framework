/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.server.elements;

import javax.swing.JTable;

import org.assertj.swing.fixture.JTableFixture;

import com.baloise.testautomation.taf.common.utils.TafProperties;

/**
 * 
 */
public class SwCell extends ASwElement {

  private int row, column;

  public SwCell(long tid, int row, int column, JTable t) {
    super(tid, t);
    this.row = row;
    this.column = column;
  }

  // public void click() {
  // Rectangle cellRect = getComponent().getCellRect(row, column, true);
  // cellRect.x = getComponent().getLocationOnScreen().x + cellRect.x;
  // cellRect.y = getComponent().getLocationOnScreen().y + cellRect.y;
  // Point p = new Point(new Double(cellRect.getCenterX()).intValue(), new Double(cellRect.getCenterY()).intValue());
  // click(p, InputEvent.BUTTON1_DOWN_MASK);
  // }

  @Override
  public TafProperties basicExecCommand(TafProperties props) {
    return new TafProperties();
  }

  @Override
  public void fillProperties() {}

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
    return "cell";
  }

}
