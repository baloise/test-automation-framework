package com.baloise.testautomation.taf.swing.base._interfaces;

import com.baloise.testautomation.taf.common.interfaces.ISwElement;

public interface ISwRadioButton<R> extends ISwElement<R> {

  public enum Command {
    check, uncheck, ischecked
  }

  public static String paramState = "state";

  public final String type = "radiobutton";

  public void check();

  public boolean isChecked();

  public void uncheck();

}
