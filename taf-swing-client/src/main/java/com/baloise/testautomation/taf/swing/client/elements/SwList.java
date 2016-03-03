package com.baloise.testautomation.taf.swing.client.elements;

import com.baloise.testautomation.taf.base._base.AElement;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwList;

public class SwList extends AElement {

  @Override
  public void click() {}

  public ISwList<?> find() {
    return (ISwList<?>)swFind(ISwList.type);
  }

  public Long getSize() {
    return find().getSize();
  }

  public String getTextAt(Long index) {
    return find().getTextAt(index);
  }

}
