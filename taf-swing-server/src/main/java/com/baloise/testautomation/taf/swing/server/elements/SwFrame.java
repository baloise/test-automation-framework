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
import java.awt.Frame;

/**
 * 
 */
public class SwFrame extends ASwElement {

  /**
   * @param tagName
   * @param tid
   * @param component
   */
  public SwFrame(long tid, Component component) {
    super(tid, component);
  }

  @Override
  public void fillProperties() {
    addProperty("title", getComponent().getTitle());
  }

  @Override
  public Frame getComponent() {
    return (Frame)component;
  }

  @Override
  public String getType() {
    return "frame";
  }

}
