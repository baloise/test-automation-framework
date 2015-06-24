/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.server.elements;

import javax.swing.JTabbedPane;

/**
 * 
 */
public class SwTabbedPane extends ASwElement {

  public SwTabbedPane(long tid, JTabbedPane component) {
    super(tid, component);
  }

  @Override
  public void fillProperties() {
    addProperty("selectedIndex", getComponent().getSelectedIndex());
    addProperty("tabCount", getComponent().getTabCount());
    addProperty("selectedTabTitle", getComponent().getTitleAt(getComponent().getSelectedIndex()));
  }

  @Override
  public JTabbedPane getComponent() {
    return (JTabbedPane)component;
  }

  @Override
  public String getType() {
    return "tabbedpane";
  }

}
