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

/**
 * 
 */
public class SwColumnHeader extends ASwElement {

  private int column;
  
  public SwColumnHeader(long tid, int column, JTable c) {
    super(tid, c);
    this.column = column; 
  }

  @Override
  public JTable getComponent() {
    return (JTable)component;
  }

  @Override
  public void fillProperties() {}

  @Override
  public String getType() {
    return "header";
  }



}
