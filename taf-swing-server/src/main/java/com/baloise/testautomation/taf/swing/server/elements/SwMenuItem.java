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

import javax.swing.JMenuItem;

import org.assertj.swing.fixture.AbstractComponentFixture;
import org.assertj.swing.fixture.JMenuItemFixture;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwMenuItem;

/**
 * 
 */
public class SwMenuItem extends ASwElement implements ISwMenuItem<Component> {

  /**
   * @param tagName
   * @param tid
   * @param component
   */
  public SwMenuItem(long tid, JMenuItem component) {
    super(tid, component);
  }

  public void clear() {}

  public void click() {
    JMenuItemFixture mif = new JMenuItemFixture(getRobot(), getComponent());
    System.out.println("Try to click on: " + mif.toString());
    mif.click();
  }

  @Override
  public void fillProperties() {
    addProperty("text", getComponent().getText());
  }

  @Override
  public JMenuItem getComponent() {
    return (JMenuItem)component;
  }

  @Override
  public String getType() {
    return ISwMenuItem.type;
  }

  @Override
  public TafProperties basicExecCommand(TafProperties props) {
    return new TafProperties();
  }

  @Override
  public JMenuItemFixture getFixture() {
    return new JMenuItemFixture(getRobot(), getComponent());
  }

}
