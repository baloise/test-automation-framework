package com.baloise.testautomation.taf.swing.client.elements;

import com.baloise.testautomation.taf.base._base.AElement;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTree;

public class SwTree extends AElement {

  @Override
  public void click() {}

  public void clickPath(String path) {
    find().clickPath(path);
  }

  public void doubleClickEachElement(String path) {
    find().doubleClickEachElement(path);
  }

  public void doubleClickPath(String path) {
    find().doubleClickPath(path);
  }

  public ISwTree<?> find() {
    return (ISwTree<?>)swFind(ISwTree.type);
  }

  public void rightClickPath(String path) {
    find().rightClickPath(path);
  }
  
}
