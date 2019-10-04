/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.base.testing;


/**
 * 
 */
public class ScreenshotOnFailed extends ScreenshotRule {

  public ScreenshotOnFailed(String path) {
    super(path, false, false, true);
  }

}
