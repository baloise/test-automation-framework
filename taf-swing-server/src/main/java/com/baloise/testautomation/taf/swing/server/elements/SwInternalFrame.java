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

import org.assertj.swing.fixture.AbstractComponentFixture;
import org.assertj.swing.fixture.JInternalFrameFixture;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwInternalFrame;

/**
 * 
 */
public class SwInternalFrame extends ASwElement implements ISwInternalFrame<Component> {

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

  @Override
  public TafProperties basicExecCommand(TafProperties props) {
    return new TafProperties();
  }

  @Override
  public JInternalFrameFixture getFixture() {
    return new JInternalFrameFixture(getRobot(), getComponent());
  }

}
