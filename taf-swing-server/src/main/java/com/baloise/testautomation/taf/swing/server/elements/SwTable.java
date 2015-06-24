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

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * 
 */
public class SwTable extends ASwElement {

  public SwTable(long tid, JTable c) {
    super(tid, c);
  }

  @Override
  public JTable getComponent() {
    return (JTable)component;
  }

  @Override
  public void fillProperties() {}

  @Override
  public String getType() {
    return "table";
  }


}
