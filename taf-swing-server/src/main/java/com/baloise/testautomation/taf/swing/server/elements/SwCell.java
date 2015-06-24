/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.server.elements;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.InputEvent;

import javax.swing.JTable;

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

  public void click() {
    Rectangle cellRect = getComponent().getCellRect(row, column, true);
    cellRect.x = getComponent().getLocationOnScreen().x + cellRect.x;
    cellRect.y = getComponent().getLocationOnScreen().y + cellRect.y;
    Point p = new Point(new Double(cellRect.getCenterX()).intValue(), new Double(cellRect.getCenterY()).intValue());
    click(p, InputEvent.BUTTON1_DOWN_MASK);
  }

  @Override
  public void fillProperties() {}
  
  @Override
  public JTable getComponent() {
    return (JTable)component;
  }

  @Override
  public String getType() {
    return "cell";
  }

}
