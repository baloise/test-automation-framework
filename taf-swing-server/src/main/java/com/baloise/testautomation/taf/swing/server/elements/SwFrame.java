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

import org.assertj.swing.fixture.FrameFixture;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwFrame;

/**
 * 
 */
public class SwFrame extends ASwElement implements ISwFrame<Component> {

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
    return ISwFrame.type;
  }

  @Override
  public TafProperties basicExecCommand(TafProperties props) {
    return new TafProperties();
  }

  @Override
  public FrameFixture getFixture() {
    return new FrameFixture(getRobot(), getComponent());
  }

}
