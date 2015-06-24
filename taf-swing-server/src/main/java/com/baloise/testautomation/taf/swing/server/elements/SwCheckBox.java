/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.server.elements;

import javax.swing.JCheckBox;

/**
 * 
 */
public class SwCheckBox extends ASwElement {

  /**
   * @param tagName
   * @param tid
   * @param component
   */
  public SwCheckBox(long tid, JCheckBox component) {
    super(tid, component);
  }

  @Override
  public void fillProperties() {
    addProperty("label", getComponent().getText());
  }

  @Override
  public JCheckBox getComponent() {
    return (JCheckBox)component;
  }

  @Override
  public String getType() {
    return "checkbox";
  }

}
