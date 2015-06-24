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

import javax.swing.JInternalFrame;

/**
 * 
 */
public class SwInternalFrame extends ASwElement {

  /**
   * @param tagName
   * @param tid
   * @param component
   */
  public SwInternalFrame(long tid, JInternalFrame component) {
    super(tid, component);
  }

  @Override
  public void fillProperties() {
    addProperty("title", getComponent().getTitle());
  }

  /** 
   * {@inheritDoc}
   */
  @Override
  public JInternalFrame getComponent() {
    return (JInternalFrame)component;
  }

  @Override
  public String getType() {
    return "internalframe";
  }

}
