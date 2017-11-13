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
    startinstrumentation, findelementbyxpath, findelementsbyxpath, sendkeys, storehierarchy, setdelaybetweenkeystrokes, seteventpostingdelay, setdelaybetweenevents, settreeseparator
  }

  public final String paramCommand = "command";
  public final String paramId = "id";
  public final String paramType = "type";
  public final String paramJavaClassPath = "javaclasspath";
  public final String paramSunJavaCommand = "sunjavacommand";
  public final String paramSpy = "spy";
  public final String paramPath = "path";
  public final String paramWatch = "watch";
  public final String paramStatus = "status";
  public final String paramMessage = "message";
  public final String paramXPath = "xpath";
  public final String paramDelayBetweenEvents = "delayBetweenEvents";
  public final String paramEventPostingDelay = "eventPostingDelay";
  public final String paramDelayBetweenKeystrokes = "delayBetweenKeystrokes";
  public final String paramRoot = "root";
  public final String paramKeys = "keys";
  public final String valueStarted = "started";
  public final String type = "application";
  public final String paramTimeout = "timeout";
  public final String paramSeparator = "separator";

  public TafProperties execCommand(TafProperties props);

  public ISwElement findElementByXpath(Long root, String xpath);

  public List<ISwElement> findElementsByXpath(Long root, String xpath);

  public void sendKeys(String keys);

  public void startInstrumentation(String url, String javaClassPathContains, String sunJavaCommandContains);

  public void startInstrumentationWithSpy(String url, String javaClassPathContains, String sunJavaCommandContains,
      String filename);

  public void storeHierarchy(String path);

  public void setDelayBetweenEvents(Long ms);

  public void setEventPostingDelay(Long ms);

  public void setDelayBetweenKeystrokes(Long ms);

  public void setTreeSeparator(String separator);

  public boolean getFailOnCommandErrors();
  
}
