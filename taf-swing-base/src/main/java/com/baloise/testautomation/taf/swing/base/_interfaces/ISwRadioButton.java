package com.baloise.testautomation.taf.swing.base._interfaces;

import com.baloise.testautomation.taf.common.interfaces.ISwElement;

public interface ISwRadioButton<R> extends ISwElement<R> {

  public enum Command {
    check, uncheck, ischecked, isenabled
  }

  public static String paramState = "state";

  public static String paramIsEnabled = "isenabled";

  public final String type = "radiobutton";

  public void check();

  public boolean isChecked();
  
  public boolean isEnabled();

  public void uncheck();

}
