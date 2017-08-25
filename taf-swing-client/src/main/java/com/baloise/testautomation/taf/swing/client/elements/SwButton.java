package com.baloise.testautomation.taf.swing.client.elements;

import com.baloise.testautomation.taf.base._base.AButton;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwButton;

public class SwButton extends AButton {

  @Override
  public void click() {
    find().click();
  }

  public ISwButton<?> find() {
    return (ISwButton<?>)swFind(ISwButton.type);
  }

  public boolean isEnabled() {
    return find().isEnabled();
  }
  
  public String getText() {
    return find().getText();
  }
  
}
