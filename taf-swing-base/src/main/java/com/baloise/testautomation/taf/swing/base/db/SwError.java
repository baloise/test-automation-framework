/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.base.db;


/**
 * 
 */
@SuppressWarnings("serial")
public class SwError extends RuntimeException {

  public SwError(Throwable t) {
    super(t);
  }

  public SwError(String message) {
    super(message);
  }

}
