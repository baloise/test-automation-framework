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


  public SwCell(long tid, int row, int column, JTable t) {
    super(tid, t);
  }

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
