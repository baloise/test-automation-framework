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

import javax.swing.JList;

import org.assertj.swing.fixture.JListFixture;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwList;

/**
 * 
 */
public class SwList extends ASwElement implements ISwList<Component> {

  public SwList(long tid, JList component) {
    super(tid, component);
  }

  @Override
  public TafProperties basicExecCommand(TafProperties props) {
    Command c = getCommand(Command.class, props.getString(paramCommand));
    switch (c) {
      case gettextat:
        String text = getTextAt(props.getLong(paramIndex));
        props.clear();
        props.putObject(paramText, text);
        break;
      default:
        throw new NotSupportedException("command not implemented: " + c);
    }
    return props;
  }

  @Override
  public void fillProperties() {
    addProperty("size", getComponent().getModel().getSize());
  }

  @Override
  public JList getComponent() {
    return (JList)component;
  }

  @Override
  public JListFixture getFixture() {
    return new JListFixture(getRobot(), getComponent());
  }

  @Override
  public String getType() {
    return ISwList.type;
  }

  @Override
  public String getTextAt(Long index) {
    return getFixture().valueAt(index.intValue());
  }


}
