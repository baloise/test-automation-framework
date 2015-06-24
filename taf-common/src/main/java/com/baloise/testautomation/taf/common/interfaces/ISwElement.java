/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.common.interfaces;

import com.baloise.testautomation.taf.common.utils.TafProperties;


/**
 * 
 */
public interface ISwElement<R> {

  public final String type = "element";
  
  public R getReference();
  public String getType();
  public void setApplication(ISwApplication application);
  public void setReference(R reference);
  public TafProperties getProperties();
  public void setProperties(TafProperties props);
  
}
