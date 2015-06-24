package com.baloise.testautomation.taf.base._interfaces;

import java.lang.annotation.Annotation;

public interface ISwingFinder {

  public void clear(Long tid);

  public void click(Long tid);

  public Long find(Annotation annotation);

  public Long find(Long root, Annotation annotation);

  public String getText(Long tid);

  public void setText(Long tid, String text);

}
