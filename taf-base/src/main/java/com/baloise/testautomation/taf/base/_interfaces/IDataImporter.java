/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.base._interfaces;

import java.util.Collection;

import com.baloise.testautomation.taf.base.types.TafId;

/**
 * 
 */
public interface IDataImporter {

  public int getNrOfDataRowsWith(TafId id);

  public Collection<IDataRow> getWith(TafId id);

}
