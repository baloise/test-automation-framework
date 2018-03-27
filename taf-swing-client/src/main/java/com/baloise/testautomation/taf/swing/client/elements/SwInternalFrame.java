package com.baloise.testautomation.taf.swing.client.elements;

import com.baloise.testautomation.taf.base._base.AElement;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwInternalFrame;

public class SwInternalFrame extends AElement {

  public ISwInternalFrame<?> find() {
    return (ISwInternalFrame<?>)swFind(ISwInternalFrame.type);
  }

  @Override
  public void click() {}
  
  public String getTitle() {
    return find().getTitle();
  }

  public void resizeTo(Long width, Long height) {
    find().resizeTo(width, height);
  }

  public void moveToFront() {
    find().moveToFront();
  }
  
}
