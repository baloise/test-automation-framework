package com.baloise.testautomation.taf.swing.base._interfaces;

import com.baloise.testautomation.taf.common.interfaces.ISwElement;

public interface ISwTree<R> extends ISwElement<R> {

  public enum Command {
    clickpath, doubleclickeachelement, doubleclickpath, rightclickpath
  }

  public final String type = "tree";
  public final String paramPath = "path";

  public void clickPath(String path);
  
  public void doubleClickEachElement(String path);
  
  public void doubleClickPath(String path);
  
  public void rightClickPath(String path);

}
