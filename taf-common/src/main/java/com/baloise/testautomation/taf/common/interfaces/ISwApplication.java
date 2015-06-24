/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.common.interfaces;

import java.util.List;

import com.baloise.testautomation.taf.common.utils.TafProperties;

/**
 * 
 */

@SuppressWarnings("hiding")
public interface ISwApplication<ISwElement> extends IFinder<ISwElement> {

  public enum Command {
    startinstrumentation, findelementbyxpath, findelementsbyxpath, execute
  }

  public final String type = "application";

  public TafProperties execCommand(String type, String command, TafProperties props);

  public void startJNLPInstrumentation(String url);

  public ISwElement findElementByXpath(Long root, String xpath);

  public List<ISwElement> findElementsByXpath(Long root, String xpath);


}
