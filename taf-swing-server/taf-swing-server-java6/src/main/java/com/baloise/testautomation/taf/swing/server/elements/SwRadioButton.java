package com.baloise.testautomation.taf.swing.server.elements;

import java.awt.Component;
import javax.swing.JRadioButton;
import org.assertj.swing.fixture.JRadioButtonFixture;
import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwRadioButton;

public class SwRadioButton extends ASwElement implements ISwRadioButton<Component> {

  /**
   * @param tid
   * @param component
   */
  public SwRadioButton(long tid, JRadioButton component) {
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
      case ischecked:
        props.clear();
        props.putObject(paramState, isChecked());
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

  @Override
  public void check() {
    getFixture().check();
  }

  @Override
  public void fillProperties() {
    addProperty("label", getComponent().getText());
  }

  @Override
  public JRadioButton getComponent() {
    return (JRadioButton)component;
  }

  @Override
  public JRadioButtonFixture getFixture() {
    return new JRadioButtonFixture(getRobot(), getComponent());
  }

  @Override
  public String getType() {
    return ISwRadioButton.type;
  }

  @Override
  public boolean isChecked() {
    try {
      getFixture().requireSelected();
      return true;
    }
    catch (Exception e) {}
    return false;
  }

  @Override
  public void uncheck() {
    getFixture().uncheck();
  }

}
