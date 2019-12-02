package com.baloise.testautomation.taf.swing.server.elements;

import java.awt.Component;

import org.assertj.swing.fixture.AbstractComponentFixture;

import com.baloise.testautomation.taf.common.utils.TafProperties;

public class SwUnsupportedElement extends ASwElement {

  public SwUnsupportedElement(long tid, Component component) {
    super(tid, component);
  }

  @Override
  public TafProperties basicExecCommand(TafProperties props) {
    throw new IllegalArgumentException("unsupported element");
  }

  @Override
  public void fillProperties() {}

  @Override
  public Component getComponent() {
    return component;
  }

  @Override
  public AbstractComponentFixture getFixture() {
    return null;
  }

  @Override
  public String getType() {
    Component c = getComponent();
    if (c == null) {
      return "unsupportedelement-nullcomponent";
    }
    return "unsupportedelement-" + getComponent().getClass().getSimpleName();
  }

}
