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

import org.assertj.swing.fixture.JMenuItemFixture;

/**
 * 
 */
public class SwMenuItem extends ASwElement {

  /**
   * @param tagName
   * @param tid
   * @param component
   */
  public SwMenuItem(long tid, JMenuItem component) {
    super(tid, component);
  }

  public void clear() {
  }

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
    return "menuitem";
  }

}
