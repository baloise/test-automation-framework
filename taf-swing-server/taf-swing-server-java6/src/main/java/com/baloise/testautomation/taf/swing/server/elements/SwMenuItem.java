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

import javax.swing.JMenuItem;

import org.assertj.swing.fixture.JMenuItemFixture;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwMenuItem;

/**
 * 
 */
public class SwMenuItem extends ASwElement implements ISwMenuItem<Component> {

  /**
   * @param tagName
   * @param tid
   * @param component
   */
  public SwMenuItem(long tid, JMenuItem component) {
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
  public JMenuItem getComponent() {
    return (JMenuItem)component;
  }

  @Override
  public JMenuItemFixture getFixture() {
    return new JMenuItemFixture(getRobot(), getComponent());
  }

  @Override
  public String getType() {
    return ISwMenuItem.type;
  }

}
