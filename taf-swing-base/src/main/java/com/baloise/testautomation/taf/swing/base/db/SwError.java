package com.baloise.testautomation.taf.swing.base.db;

@SuppressWarnings("serial")
public class SwError extends RuntimeException {

  public SwError(String message) {
    super(message);
  }

  public SwError(Throwable t) {
    super(t);
  }

}
