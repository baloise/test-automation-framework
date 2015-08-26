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

import javax.swing.JTabbedPane;

import org.assertj.swing.fixture.JTabbedPaneFixture;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTabbedPane;

/**
 * 
 */
public class SwTabbedPane extends ASwElement implements ISwTabbedPane<Component> {

  public SwTabbedPane(long tid, JTabbedPane component) {
    super(tid, component);
  }

  @Override
  public TafProperties basicExecCommand(TafProperties props) {
    Command c = getCommand(Command.class, props.getString(paramCommand));
    switch (c) {
      case selectbytitle:
        selectByTitle(props.getString(paramTitle));
        props.clear();
        break;
      default:
        throw new NotSupportedException("command not implemented: " + c);
    }
    return props;
  }

  @Override
  public void fillProperties() {
    addProperty("selectedIndex", getComponent().getSelectedIndex());
    addProperty("tabCount", getComponent().getTabCount());
    addProperty("selectedTabTitle", getComponent().getTitleAt(getComponent().getSelectedIndex()));
    for (int i = 0; i < getComponent().getTabCount(); i++) {
      addProperty("title-" + i, getComponent().getTitleAt(i));
    }
  }

  @Override
  public JTabbedPane getComponent() {
    return (JTabbedPane)component;
  }

  @Override
  public JTabbedPaneFixture getFixture() {
    return new JTabbedPaneFixture(getRobot(), getComponent());
  }

  @Override
  public String getType() {
    return ISwTabbedPane.type;
  }

  public void selectByTitle(String title) {
    getFixture().selectTab(title);
  }

}
