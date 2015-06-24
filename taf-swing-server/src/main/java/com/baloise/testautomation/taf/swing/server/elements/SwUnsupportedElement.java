package com.baloise.testautomation.taf.swing.server.elements;

import java.awt.Component;

public class SwUnsupportedElement extends ASwElement {

	public SwUnsupportedElement(long tid, Component component) {
		super(tid, component);
	}

	@Override
	public Component getComponent() {
		return component;
	}

  @Override
  public void fillProperties() {
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
