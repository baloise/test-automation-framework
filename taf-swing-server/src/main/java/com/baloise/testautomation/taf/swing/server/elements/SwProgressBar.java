package com.baloise.testautomation.taf.swing.server.elements;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwProgressBar;
import org.assertj.swing.fixture.JProgressBarFixture;

import javax.swing.*;
import java.awt.*;

public class SwProgressBar extends ASwElement implements ISwProgressBar<Component> {

  public SwProgressBar(long tid, JProgressBar component) {
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
      case getvalue:
        props.clear();
        props.putObject(paramValue, getValue());
        break;
      case getmaximum:
        props.clear();
        props.putObject(paramMaximum, getMaximum());
        break;
      case getminimum:
        props.clear();
        props.putObject(paramMinimum, getMinimum());
        break;
      case click:
        props.clear();
        click();
        break;
      case isindeterminate:
        props.clear();
        props.putObject(paramIsIndeterminate, isIndeterminate());
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
    addProperty("value", getValue());
  }

  @Override
  public JProgressBar getComponent() {
    return (JProgressBar) component;
  }

  @Override
  public JProgressBarFixture getFixture() {
    return new JProgressBarFixture(getRobot(), getComponent());
  }

  @Override
  public String getType() {
    return ISwProgressBar.type;
  }

  @Override
  public void rightClick() {
    getFixture().rightClick();
  }

  public String getText() {
    return getFixture().text();
  }

  public int getValue() {
    return getComponent().getValue();
  }

  public int getMaximum() {
    return getComponent().getMaximum();
  }

  public int getMinimum() {
    return getComponent().getMinimum();
  }

  public boolean isIndeterminate() {
    return getComponent().isIndeterminate();
  }

}
