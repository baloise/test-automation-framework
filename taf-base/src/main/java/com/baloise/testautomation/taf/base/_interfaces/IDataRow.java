/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.base._interfaces;

import java.util.Hashtable;

import com.baloise.testautomation.taf.base.types.TafId;

/**
 * 
 */
public interface IDataRow {

  public IType get(String columnName);

  public Hashtable<String, IType> getData();

  public TafId getId();

  public void set(String columnName, IType data);

  public void setId(TafId id);

}
