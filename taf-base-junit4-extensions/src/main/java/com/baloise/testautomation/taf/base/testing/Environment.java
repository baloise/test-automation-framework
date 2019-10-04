package com.baloise.testautomation.taf.base.testing;

public interface Environment {

  public interface ACC {}

  public interface INT {}

  public interface LOCAL {}

  public interface TEST {}

  public void setEnvironment(Class<?> i);

}
