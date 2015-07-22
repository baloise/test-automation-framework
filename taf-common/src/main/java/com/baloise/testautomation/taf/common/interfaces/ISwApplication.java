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

  public final String paramCommand = "command";
  public final String paramId = "id";
  public final String paramType = "type";
  public final String paramSpy = "spy";
  public final String paramPath = "path";
  public final String paramWatch = "watch";
  public final String paramStatus = "status";
  public final String paramMessage = "message";
  public final String paramXPath = "xpath";
  public final String paramRoot = "root";
  
  public final String valueStarted = "started";
  
  public enum Command {
    startinstrumentation, findelementbyxpath, findelementsbyxpath, storehierarchy
  }

  public final String type = "application";

  public TafProperties execCommand(TafProperties props);

  public void startJNLPInstrumentation(String url);
  public void startJNLPInstrumentationWithSpy(String url, String filename);

  public ISwElement findElementByXpath(Long root, String xpath);

  public List<ISwElement> findElementsByXpath(Long root, String xpath);

  public void storeHierarchy(String path);
  
}
