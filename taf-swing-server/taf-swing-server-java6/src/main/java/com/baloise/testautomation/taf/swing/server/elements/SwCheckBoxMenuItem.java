package com.baloise.testautomation.taf.swing.server.elements;

import java.awt.Component;

import javax.swing.JCheckBoxMenuItem;

import org.assertj.swing.fixture.JMenuItemFixture;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwCheckBoxMenuItem;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwMenuItem;

public class SwCheckBoxMenuItem extends ASwElement implements ISwCheckBoxMenuItem<Component> {

	public SwCheckBoxMenuItem(long tid, JCheckBoxMenuItem component) {
		super(tid, component);
	}

	@Override
	public TafProperties basicExecCommand(TafProperties props) {
    Command c = getCommand(Command.class, props.getString(paramCommand));
    switch (c) {
      case click:
        props.clear();
        click();
        break;
      case isenabled:
        props.clear();
        props.putObject(paramIsEnabled, isEnabled());
        break;
      case getstate:
      	props.clear();
      	props.putObject(paramState, getState());
      	break;
      default:
        throw new IllegalArgumentException("command not implemented: " + c);
    }
    return props;
	}

	public void clear() {}

  public void click() {
    getFixture().click();
  }

  @Override
  public void fillProperties() {
    addProperty("text", getComponent().getText());
  }

  @Override
  public JCheckBoxMenuItem getComponent() {
    return (JCheckBoxMenuItem)component;
  }

  @Override
  public JMenuItemFixture getFixture() {
    return new JMenuItemFixture(getRobot(), getComponent());
  }

  @Override
  public String getType() {
    return ISwMenuItem.type;
  }

	@Override
	public boolean getState() {
		return getFixture().targetCastedTo(JCheckBoxMenuItem.class).getState();
	}

}
