package com.baloise.testautomation.taf.swing.base._interfaces;

import com.baloise.testautomation.taf.common.interfaces.ISwElement;

public interface ISwInternalFrame<R> extends ISwElement<R> {

  public enum Command {
    click, gettitle, resizeto, movetofront;
  }

  public final String type = "internalframe";
  
  public final String paramWidth = "width";
  public final String paramHeight = "height";
  public final String paramTitle = "title";

  public void click();
  
  public String getTitle();
  
  public void resizeTo(Long width, Long height);
  
  public void moveToFront();

}
