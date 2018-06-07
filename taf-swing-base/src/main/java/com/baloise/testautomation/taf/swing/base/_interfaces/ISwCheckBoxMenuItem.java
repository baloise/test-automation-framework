package com.baloise.testautomation.taf.swing.base._interfaces;

import com.baloise.testautomation.taf.common.interfaces.ISwElement;

public interface ISwCheckBoxMenuItem<R> extends ISwElement<R> {

  public enum Command {
    click, isenabled, getstate
  }
  
  public final String type = "checkboxmenuitem";
  
  public final String paramIsEnabled = "isenabled";
  
  public final String paramState = "state";
  
  public void click();
  
  public boolean isEnabled();
  
  public boolean getState();
}
