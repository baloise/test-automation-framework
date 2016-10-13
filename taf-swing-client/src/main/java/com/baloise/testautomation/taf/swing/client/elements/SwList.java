package com.baloise.testautomation.taf.swing.client.elements;

import com.baloise.testautomation.taf.base._base.AElement;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwList;

public class SwList extends AElement {

  @Override
  public void click() {}

  public ISwList<?> find() {
    return (ISwList<?>)swFind(ISwList.type);
  }

  public Long findListItemContaining(String partialText) {
    Long size = getSize();
    if (size == null) {
      return null;
    }
    for (long l = 0; l < size.longValue(); l++) {
      String s = getTextAt(l);
      if (s != null) {
        if (s.contains(partialText)) {
          return l;
        }
      }
    }
    return null;
  }

  public Long getSize() {
    return find().getSize();
  }

  public String getTextAt(Long index) {
    return find().getTextAt(index);
  }
  
  public void clickItem(Long index) {
    find().clickItem(index);
  }

  public boolean hasListItemContaining(String partialText) {
    return findListItemContaining(partialText) != null;
  }

}
