package com.baloise.testautomation.taf.swing.client.elements;

import com.baloise.testautomation.taf.base._base.AElement;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwDialog;

public class SwDialog extends AElement {

  public ISwDialog<?> find() {
    return (ISwDialog<?>)swFind(ISwDialog.type);
  }

  @Override
  public void click() {}
  
  public String getTitle() {
    return find().getTitle();
  }

  public void resizeTo(Long width, Long height) {
    find().resizeTo(width, height);
  }

}
