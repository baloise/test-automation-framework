package com.baloise.testautomation.taf.common.interfaces;

import java.lang.annotation.Annotation;
import java.util.concurrent.Callable;

public interface IFinder<Element> {

  public Element find(Annotation annotation);

  public Element find(Element root, Annotation annotation);

  public void setTimeoutInMsecs(Long msecs);
  
  public Long getTimeoutInMsecs();

  public void setDefaultTimeoutInMsecs();

  void safeInvoke(RuntimeException exception, Runnable runnable);

  <T> T safeInvoke(RuntimeException exception, Callable<T> callable);

  void safeInvoke(Runnable runnable);

  <T> T safeInvoke(Callable<T> callable);

}
