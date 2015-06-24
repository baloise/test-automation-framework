package com.baloise.testautomation.taf.swing.server.elements;

import javax.swing.JButton;

import org.assertj.swing.fixture.JButtonFixture;

import com.baloise.testautomation.taf.swing.base._interfaces.ISwButton;

public class SwButton extends ASwElement {

  public SwButton(long tid, JButton c) {
    super(tid, c);
  }

  public void click() {
    getFixture().click();
  }

  @Override
  public JButton getComponent() {
    return (JButton)component;
  }

  @Override
  public void fillProperties() {
    addProperty("text", getComponent().getText());
  }

  @Override
  public JButtonFixture getFixture() {
    return new JButtonFixture(getRobot(), getComponent());
  }

  @Override
  public String getType() {
    return ISwButton.type;
  }

}
