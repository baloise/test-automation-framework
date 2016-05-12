package com.baloise.testautomation.taf.swing.server.elements;

import java.awt.Component;

import javax.swing.JButton;

import org.assertj.swing.fixture.JButtonFixture;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwButton;

public class SwButton extends ASwElement implements ISwButton<Component> {

  public SwButton(long tid, JButton c) {
    super(tid, c);
  }

  @Override
  public TafProperties basicExecCommand(TafProperties props) {
    Command c = getCommand(Command.class, props.getString(paramCommand));
    switch (c) {
      case click:
        props.clear();
        click();
        break;
      case isEnabled:
        props.clear();
        props.putObject(paramIsEnabled, isEnabled());
        break;
      default:
        throw new NotSupportedException("command not implemented: " + c);
    }
    return props;
  }

  public void click() {
    getFixture().click();
  }

  @Override
  public void fillProperties() {
    addProperty("text", getComponent().getText());
    addProperty("tooltip", getComponent().getToolTipText());
  }

  @Override
  public JButton getComponent() {
    return (JButton)component;
  }

  @Override
  public JButtonFixture getFixture() {
    return new JButtonFixture(getRobot(), getComponent());
  }

  @Override
  public String getType() {
    return ISwButton.type;
  }

  /** 
   * {@inheritDoc}
   */
  @Override
  public boolean isEnabled() {
    return getFixture().isEnabled();
  }

}
