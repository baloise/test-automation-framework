/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.base._base;

import java.util.List;
import java.util.Vector;

import com.baloise.testautomation.taf.base._interfaces.IDataRow;
import com.baloise.testautomation.taf.base.types.TafString;

import static com.baloise.testautomation.taf.base._base.TafAssert.assertTrue;

/**
 * 
 */
public abstract class ATable extends ABase {

  public List<IDataRow> fillDataRows = new Vector<IDataRow>();
  public List<IDataRow> checkDataRows = new Vector<IDataRow>();

  @Override
  public void check() {
    if (isCheckCustom()) {
      checkCustom();
      return;
    }
    for (IDataRow data : checkDataRows) {
      setCheckFields(data);
      setCheckDataFields(data);
      check(data);
    }
  }

  public abstract void check(IDataRow data);

  @Override
  public void fill() {
    if (isFillCustom()) {
      fillCustom();
      return;
    }
    for (IDataRow data : fillDataRows) {
      setFillFields(data);
      setFillDataFields(data);
      fill(data);
    }
  }

  public abstract void fill(IDataRow data);

  @Override
  public void setCheck(TafString id) {
    checkId = id;
    checkDataRows.clear();
    if (id.isCustom()) {
      return;
    }
    if (!id.isSkip()) {
      checkDataRows.addAll(loadCheck(checkId.asString()));
      assertTrue("no data found: '" + id + "' --> " + this.getClass(), checkDataRows.size() > 0);
    }
  }

  @Override
  public void setFill(TafString id) {
    fillId = id;
    fillDataRows.clear();
    if (id.isCustom()) {
      return;
    }
    if (!id.isSkip()) {
      fillDataRows.addAll(loadFill(fillId.asString()));
      assertTrue("no data found: '" + id + "' --> " + this.getClass(), fillDataRows.size() > 0);
    }
  }

}
