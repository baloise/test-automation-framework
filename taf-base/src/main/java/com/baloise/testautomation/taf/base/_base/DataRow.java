/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.base._base;

import java.util.Hashtable;

import com.baloise.testautomation.taf.base._interfaces.IDataRow;
import com.baloise.testautomation.taf.base._interfaces.IType;
import com.baloise.testautomation.taf.base.types.TafId;

/**
 * 
 */
public class DataRow implements IDataRow {

  private TafId id = null;
  private Hashtable<String, IType> data = new Hashtable<String, IType>();

  @Override
  public IType get(String columnName) {
    return data.get(columnName.toLowerCase().trim());
  }

  @Override
  public Hashtable<String, IType> getData() {
    return data;
  }

  @Override
  public TafId getId() {
    return id;
  }

  @Override
  public void set(String columnName, IType data) {
    this.data.put(columnName.toLowerCase().trim(), data);
  }

  @Override
  public void setId(TafId id) {
    this.id = id;
  }

}
