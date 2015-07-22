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

import javax.swing.JCheckBox;

import org.assertj.swing.fixture.JCheckBoxFixture;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwCheckbox;

/**
 * 
 */
public class SwCheckBox extends ASwElement implements ISwCheckbox<Component> {

  /**
   * @param tagName
   * @param tid
   * @param component
   */
  public SwCheckBox(long tid, JCheckBox component) {
    super(tid, component);
  }

  @Override
  public TafProperties basicExecCommand(TafProperties props) {
    Command c = getCommand(Command.class, props.getString(paramCommand));
    switch (c) {
      case check:
        props.clear();
        check();
        break;
      case uncheck:
        props.clear();
        uncheck();
        break;
      default:
        throw new NotSupportedException("command not implemented: " + c);
    }
    return props;
  }

  @Override
  public void check() {
    getFixture().check();
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
  public JCheckBoxFixture getFixture() {
    return new JCheckBoxFixture(getRobot(), getComponent());
  }

  @Override
  public String getType() {
    return ISwCheckbox.type;
  }

  @Override
  public void uncheck() {
    getFixture().uncheck();
  }

}
