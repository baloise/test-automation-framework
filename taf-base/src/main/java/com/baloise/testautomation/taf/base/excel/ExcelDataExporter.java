/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.base.excel;

import static org.junit.Assert.fail;

import com.baloise.testautomation.taf.base._interfaces.IDataExporter;
import com.baloise.testautomation.taf.base._interfaces.IDataRow;
import com.baloise.testautomation.taf.base.types.TafId;

/**
 * 
 */
public class ExcelDataExporter implements IDataExporter {

  @Override
  public boolean putWith(TafId id, IDataRow data) {
    fail("Not yet implemented");
    return false;
  }

}
