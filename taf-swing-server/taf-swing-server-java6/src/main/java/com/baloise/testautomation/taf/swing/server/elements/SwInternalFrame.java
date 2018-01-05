package com.baloise.testautomation.taf.swing.server.elements;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JInternalFrame;

import org.assertj.swing.fixture.JInternalFrameFixture;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwInternalFrame;

/**
 * 
 */
public class SwInternalFrame extends ASwElement implements ISwInternalFrame<Component> {

  /**
   * @param tid
   * @param component
   */
  public SwInternalFrame(long tid, JInternalFrame component) {
    super(tid, component);
  }

  @Override
  public TafProperties basicExecCommand(TafProperties props) {
    System.out.println("SwInternalFrame --> basicExecCommand");
    Command c = getCommand(Command.class, props.getString(paramCommand));
    System.out.println(c);
    switch (c) {
      case click:
        props.clear();
        click();
        break;
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
  public void click() {
    getFixture().click();
  }

  @Override
  public void fillProperties() {
    addProperty("title", getComponent().getTitle());
  }

  @Override
  public JInternalFrame getComponent() {
    return (JInternalFrame)component;
  }

  @Override
  public JInternalFrameFixture getFixture() {
    return new JInternalFrameFixture(getRobot(), getComponent());
  }

  @Override
  public String getType() {
    return ISwInternalFrame.type;
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
