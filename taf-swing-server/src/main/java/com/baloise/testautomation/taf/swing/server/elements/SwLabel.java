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

import javax.swing.JLabel;

import org.assertj.swing.fixture.JLabelFixture;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwCheckbox;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwLabel;

/**
 * 
 */
public class SwLabel extends ASwElement implements ISwLabel<Component> {

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

  @Override
  public JLabel getComponent() {
    return (JLabel)component;
  }

  @Override
  public String getType() {
    return ISwLabel.type;
  }

  @Override
  public TafProperties basicExecCommand(TafProperties props) {
    Command c = getCommand(Command.class, props.getString(paramCommand));
    switch (c) {
      case gettext:
        props.clear();
        props.putObject(paramText, getText());
        break;
      default:
        throw new IllegalArgumentException("command not implemented: " + c);
    }
    return props;
  }

  @Override
  public JLabelFixture getFixture() {
    return new JLabelFixture(getRobot(), getComponent());
  }

  @Override
  public String getText() {
    return null;
  }

}
