/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.server.elements;

import javax.swing.JLabel;

/**
 * 
 */
public class SwLabel extends ASwElement {

  /**
   * @param tagName
   * @param tid
   * @param component
   */
  public SwLabel(long tid, JLabel component) {
    super(tid, component);
  }

  @Override
  public void fillProperties() {
    addProperty("text", asValidAttribute(getComponent().getText()));
  }

  /** 
   * {@inheritDoc}
   */
  @Override
  public JLabel getComponent() {
    return (JLabel)component;
  }

  /** 
   * {@inheritDoc}
   */
  @Override
  public String getType() {
    return "label";
  }

}
