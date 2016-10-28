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
      case getsize:
        props.clear();
        props.putObject(paramSize, getSize());
        break;
      case clickitem:
        clickItem(props.getLong(paramIndex));
        break;
      case clickitembytext:
        clickItem(props.getString(paramText));
        break;
      default:
        throw new NotSupportedException("command not implemented: " + c);
    }
    return props;
  }

  @Override
  public void fillProperties() {
    try {
      addProperty("size", getComponent().getModel().getSize());
    }
    catch (Exception e) {
      addProperty("size", -1);
    }
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
  public Long getSize() {
    try {
      return new Long(getFixture().contents().length);
    }
    catch (Exception e) {
      return 0L;
    }
  }

  @Override
  public String getTextAt(Long index) {
    return getFixture().valueAt(index.intValue());
  }
  
  @Override
  public void clickItem(Long index) {
    getFixture().clickItem(index.intValue());
  }

  @Override
  public String getType() {
    return ISwList.type;
  }

  @Override
  public void clickItem(String value) {
    getFixture().clickItem(value);
  }
}
