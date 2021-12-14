package com.baloise.testautomation.taf.common.interfaces;

import java.lang.annotation.Annotation;
import java.util.concurrent.Callable;

public interface IFinder<Element> {

  public Element find(Annotation annotation);

  public Element find(Element root, Annotation annotation);

  public void setTimeoutInMsecs(Long msecs);
  
  public Long getTimeoutInMsecs();

  public void setDefaultTimeoutInMsecs();

  void safeInvoke(Error exception, Runnable runnable);

  <T> T safeInvoke(Error exception, Callable<T> callable);

  void safeInvoke(Runnable runnable);

  <T> T safeInvoke(Callable<T> callable);

  /**
   * This method is used, when the type of Element is not known at compile-time.
   *
   * @param parent The parent object, that will be casted to Element.
   * @param annotation The annotation.
   * @return An object of type Element.
   */
  default public Element findNonGeneric(Object parent, Annotation annotation) {
    return find((Element) parent, annotation);
  }
}
