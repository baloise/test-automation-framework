package com.baloise.testautomation.taf.base._interfaces;

import java.util.ArrayList;
import java.util.Collection;

public interface IDataProvider {

  public final static ArrayList<IDataRow> EMPTY_DATA = new ArrayList<IDataRow>();

  Collection<IDataRow> loadCheckData(String idAndDetail);

  Collection<IDataRow> loadFillData(String idAndDetail);
}
