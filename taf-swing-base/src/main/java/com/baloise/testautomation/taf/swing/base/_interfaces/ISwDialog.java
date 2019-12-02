package com.baloise.testautomation.taf.swing.base._interfaces;

import com.baloise.testautomation.taf.common.interfaces.ISwElement;

public interface ISwDialog<R> extends ISwElement<R> {

  public enum Command {
    gettitle, resizeto;
  }

  public final String type = "dialog";
  
  public final String paramWidth = "width";
  public final String paramHeight = "height";
  public final String paramTitle = "title";

  public String getTitle();
  
  public void resizeTo(Long width, Long height);

}
