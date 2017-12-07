package com.baloise.testautomation.taf.common.interfaces;

import java.lang.annotation.Annotation;

public interface IFinder<Element> {

  public Element find(Annotation annotation);

  public Element find(Element root, Annotation annotation);

  public void setTimeoutInMsecs(Long msecs);
  
  public Long getTimeoutInMsecs();

  public void setDefaultTimeoutInMsecs();

}
