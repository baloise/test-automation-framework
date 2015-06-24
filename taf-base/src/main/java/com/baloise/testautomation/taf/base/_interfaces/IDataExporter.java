/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.base._interfaces;

import com.baloise.testautomation.taf.base.types.TafId;

/**
 * 
 */
public interface IDataExporter {

  public boolean putWith(TafId id, IDataRow data);

}
