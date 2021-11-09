package com.baloise.testautomation.taf.base._interfaces;

import java.lang.annotation.Annotation;

public interface IElement {

  public void click();
  
  default public <T> T find() {
    return null;
  }

  public void setBy(Annotation by);

  public Annotation getBy();

  public void setCheck(Annotation check);

  public void setComponent(IComponent component);

  public void setName(String name);

}
