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
  public TafProperties basicExecCommand(TafProperties props) {
    Command c = getCommand(Command.class, props.getString(paramCommand));
    switch (c) {
      case gettext:
        props.clear();
        props.putObject(paramText, getText());
        break;
      case rightclick:
        props.clear();
        rightClick();
        break;
      case click:
        props.clear();
        click();
        break;
      case getx:
        props.clear();
        props.putObject(paramText, getX());
        break;
      case gety:
        props.clear();
        props.putObject(paramText, getY());
        break;
      default:
        throw new IllegalArgumentException("command not implemented: " + c);
    }
    return props;
  }

  @Override
  public void click() {
    getFixture().click();
  }

  @Override
  public void fillProperties() {
    addProperty("text", asEscapedXml(getComponent().getText()));
  }

  @Override
  public JLabel getComponent() {
    return (JLabel)component;
  }

  @Override
  public JLabelFixture getFixture() {
    return new JLabelFixture(getRobot(), getComponent());
  }

  @Override
  public String getText() {
    return getFixture().text();
  }

  @Override
  public String getType() {
    return ISwLabel.type;
  }

  @Override
  public void rightClick() {
    getFixture().rightClick();
  }

  @Override
  public int getX() {
    return component.getX();
  }

  @Override
  public int getY() {
    return component.getY();
  }

}
