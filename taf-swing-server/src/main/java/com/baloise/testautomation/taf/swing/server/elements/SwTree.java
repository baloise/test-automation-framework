package com.baloise.testautomation.taf.swing.server.elements;

import java.awt.Component;

import javax.swing.JTree;

import org.assertj.swing.fixture.JTreeFixture;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTree;

public class SwTree extends ASwElement implements ISwTree<Component> {

  public static String separator = null;

  public SwTree(long tid, JTree component) {
    super(tid, component);
  }

  @Override
  public TafProperties basicExecCommand(TafProperties props) {
    Command c = getCommand(Command.class, props.getString(paramCommand));
    switch (c) {
      case clickpath:
        String path = props.getString(paramPath);
        props.clear();
        clickPath(path);
        break;
      case doubleclickeachelement:
        path = props.getString(paramPath);
        props.clear();
        doubleClickEachElement(path);
        break;
      case doubleclickpath:
        path = props.getString(paramPath);
        props.clear();
        doubleClickPath(path);
        break;
      case rightclickpath:
        path = props.getString(paramPath);
        props.clear();
        rightClickPath(path);
        break;
      default:
        throw new IllegalArgumentException("command not implemented: " + c);
    }
    return props;
  }

  public void clear() {}

  public void click() {
    getFixture().click();
  }

  @Override
  public void clickPath(String path) {
    getFixture().clickPath(path);
  }

  @Override
  public void doubleClickEachElement(String path) {
    String[] elements = path.split("/");
    String partialPath = "";
    for (String element : elements) {
      partialPath = partialPath + element;
      getFixture().doubleClickPath(partialPath);
      partialPath = partialPath + "/";
    }
  }

  @Override
  public void doubleClickPath(String path) {
    getFixture().doubleClickPath(path);
  }

  @Override
  public void fillProperties() {
    addProperty("rowcount", getComponent().getRowCount());
    // TODO add more properties here
  }

  @Override
  public JTree getComponent() {
    return (JTree)component;
  }

  @Override
  public JTreeFixture getFixture() {
    JTreeFixture treeFixture = new JTreeFixture(getRobot(), getComponent());
    if (separator != null) {
      treeFixture.replaceSeparator(separator);
    }
    return treeFixture;
  }

  @Override
  public String getType() {
    return ISwTree.type;
  }

  @Override
  public void rightClickPath(String path) {
    getFixture().rightClickPath(path);
  }

}
