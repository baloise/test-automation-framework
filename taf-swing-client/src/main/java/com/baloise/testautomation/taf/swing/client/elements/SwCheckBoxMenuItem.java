package com.baloise.testautomation.taf.swing.client.elements;

import com.baloise.testautomation.taf.base._base.AElement;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwCheckBoxMenuItem;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwMenuItem;

public class SwCheckBoxMenuItem extends AElement{

  @Override
  public void click() {
    find().click();
  }
  
  public boolean isEnabled() {
    return find().isEnabled();
  }
  
  public boolean getState() {
  	return find().getState();
  }

  public ISwCheckBoxMenuItem<?> find() {
    return (ISwCheckBoxMenuItem<?>)swFind(ISwCheckBoxMenuItem.type);
  }
}
