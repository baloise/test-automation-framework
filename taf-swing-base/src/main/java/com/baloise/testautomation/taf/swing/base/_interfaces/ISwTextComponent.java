package com.baloise.testautomation.taf.swing.base._interfaces;

import com.baloise.testautomation.taf.common.interfaces.ISwElement;

public interface ISwTextComponent<R> extends ISwElement<R>  {
 
  public enum Command {
    click, clear, entertext, gettext, isenabled;
  }

  public final String paramText = "text";
  
  public final String paramIsEnabled = "isenabled";

  public final String type = "textcomponent";

  public void clear();

  public void click();

  public void enterText(String text);

  public String getText();
  
  public boolean isEnabled();
  
}
