package com.baloise.testautomation.taf.swing.server.elements;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;

import org.assertj.swing.fixture.DialogFixture;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwDialog;

/**
 * 
 */
public class SwDialog extends ASwElement implements ISwDialog<Component> {

  /**
   * @param tagName
   * @param tid
   * @param component
   */
  public SwDialog(long tid, Dialog component) {
    super(tid, component);
  }

  @Override
  public TafProperties basicExecCommand(TafProperties props) {
    System.out.println("SwDialog --> basicExecCommand");
    Command c = getCommand(Command.class, props.getString(paramCommand));
    System.out.println(c);
    switch (c) {
      case gettitle:
        props.clear();
        props.putObject(paramTitle, getTitle());
        break;
      case resizeto:
        resizeTo(props.getLong(paramWidth), props.getLong(paramHeight));
        props.clear();
        break;
    default:
      throw new IllegalArgumentException("command valid but not implemented yet: " + c);
    }
    return props;
  }
  
  @Override
  public void fillProperties() {
    addProperty("title", getComponent().getTitle());
  }

  @Override
  public Dialog getComponent() {
    return (Dialog)component;
  }

  @Override
  public DialogFixture getFixture() {
    return new DialogFixture(getRobot(), getComponent());
  }

  @Override
  public String getType() {
    return ISwDialog.type;
  }

  @Override
  public void resizeTo(Long width, Long height) {
    Dimension size = new Dimension(width.intValue(), height.intValue());
    getFixture().resizeTo(size);
  }

  @Override
  public String getTitle() {
    return getFixture().target().getTitle();
  }

}
