package com.baloise.testautomation.taf.base.types;

public class TafComboString extends TafString {

  public static TafComboString emptyString() {
    return new TafComboString("");
  }

  public static TafComboString normalString(String s) {
    if (s == null) {
      return nullString();
    }
    if (s.trim().equalsIgnoreCase(EMPTY)) {
      return emptyString();
    }
    if (s.trim().equalsIgnoreCase(NULL)) {
      return nullString();
    }
    if (s.trim().equalsIgnoreCase(SKIP)) {
      return skipString();
    }
    return new TafComboString(s);
  }

  public static TafComboString nullString() {
    return new TafComboString(null);
  }

  public static TafComboString parameterComboString(String parameterName) {
    TafComboString result = TafComboString.nullString();
    result.setParameterName(parameterName);
    return result;
  }

  public static TafComboString skipString() {
    TafComboString cs = new TafComboString(null);
    cs.setIsSkip(true);
    return cs;
  }

  public TafComboString(String s) {
    super(s);
  }
}
