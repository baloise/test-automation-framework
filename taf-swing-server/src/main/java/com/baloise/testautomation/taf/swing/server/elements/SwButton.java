package com.baloise.testautomation.taf.swing.server.elements;

import java.awt.Component;
import java.util.Enumeration;

import javax.swing.JButton;

import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.timing.Timeout;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwButton;
import com.baloise.testautomation.taf.swing.server.utils.SwRobotFactory;

public class SwButton extends ASwElement implements ISwButton<Component> {

  public SwButton(long tid, JButton c) {
    super(tid, c);
  }

  @Override
  public TafProperties basicExecCommand(TafProperties props) {
    Command c = getCommand(Command.class, props.getString(paramCommand));
    System.out.println("command: " + c);
    switch (c) {
      case click:
        props.clear();
        click();
        break;
      case isenabled:
        props.clear();
        props.putObject(paramIsEnabled, isEnabled());
        break;
      default:
        throw new NotSupportedException("command not implemented: " + c);
    }
    return props;
  }

  public void click() {
    getFixture().requireEnabled(Timeout.timeout());
    try {
      Thread.sleep(SwRobotFactory.delayBetweenEvents);
    }
    catch (InterruptedException e) {
      e.printStackTrace();
    }
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
