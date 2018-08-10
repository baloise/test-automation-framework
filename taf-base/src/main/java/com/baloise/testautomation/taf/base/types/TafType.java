package com.baloise.testautomation.taf.base.types;

import com.baloise.testautomation.taf.base._interfaces.IType;

public abstract class TafType implements IType {

  public static final String EMPTY = "{empty}";
  public static final String NULL = "{null}";
  public static final String SKIP = "{skip}";
  public static final String TODAY = "{today}";
  public static final String SETMARKER = "{set}";
  public static final String GETMARKER = "{get}";
  public static final String CUSTOM = "{custom}";

  public static String GetGetMarker() {
    return GETMARKER;
  }

  public static String GetSetMarker() {
    return SETMARKER;
  }

  private String parameterName = "";

  protected boolean isEmpty = false;
  protected boolean isSkip = false;
  protected boolean isCustom = false;
  protected Object value = null;

  public static String getCustom(String value) {
    if (value == null) {
      return null;
    }
    if (value.trim().toLowerCase().startsWith(CUSTOM.toLowerCase())) {
      return value.substring(CUSTOM.length(), value.length());
    }
    return null;
  }

  @Override
  public String getCustom() {
    return TafType.getCustom(asString());
  }

  private void setFlags(IType result) {
    result.setIsEmpty(isEmpty());
    result.setIsSkip(isSkip());
    result.setIsCustom(isCustom());
  }
  
  @Override
  public TafBoolean asTafBoolean() {
    TafBoolean result = TafBoolean.normalBoolean(asString());
    setFlags(result);
    return result;
  }

  @Override
  public TafDate asTafDate() {
    TafDate result = TafDate.normalDate(asString());
    setFlags(result);
    return result;
  }

  @Override
  public TafDouble asTafDouble() {
    TafDouble result = TafDouble.normalDouble(asString());
    setFlags(result);
    return result;
  }

  @Override
  public TafInteger asTafInteger() {
    TafInteger result = TafInteger.normalInteger(asString());
    setFlags(result);
    return result;
  }

  @Override
  public TafString asTafString() {
    TafString result = new TafString(asString());
    setFlags(result);
    return result;
  }

  protected abstract void basicSet(String s);

  @Override
  public String getParameterName() {
    if (parameterName != null) {
      return parameterName;
    }
    return "";
  }

  @Override
  public boolean isEmpty() {
    return isEmpty;
  }

  @Override
  public boolean isNotNull() {
    return !isNull();
  }

  @Override
  public boolean isNull() {
    if (isEmpty || isSkip) {
      return false;
    }
    return (value == null);
  }

  @Override
  public boolean isParameter() {
    if (parameterName != null) {
      if (!parameterName.isEmpty()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isSkip() {
    return isSkip;
  }

  @Override
  public boolean isCustom() {
    return isCustom;
  }

  @Override
  public void set(String s) {
    setIsSkip(false);
    setIsEmpty(false);
    setIsCustom(false);
    if (s == null) {
      value = s;
      return;
    }
    if (s.isEmpty()) {
      setIsEmpty(true);
    }
    if (SKIP.equalsIgnoreCase(s)) {
      setIsSkip(true);
    }
    String custom = getCustom(s);
    if (custom != null) {
      setIsCustom(true);
      value = custom;
      return;
    }
    basicSet(s);
  }

  @Override
  public void setIsEmpty(boolean isEmpty) {
    this.isEmpty = isEmpty;
  }

  @Override
  public void setIsSkip(boolean isSkip) {
    this.isSkip = isSkip;
  }

  @Override
  public void setIsCustom(boolean isCustom) {
    this.isCustom = isCustom;
  }

  @Override
  public void setParameterName(String parameterName) {
    this.parameterName = parameterName;
  }

  @Override
  public String toString() {
    if (value != null) {
      return ("IsNull = " + isNull() + ", IsEmpty = " + isEmpty() + ", IsSkip = " + isSkip() + ", IsCustom = "
          + isCustom() + ", Wert = " + value.toString());
    }
    return ("IsNull = " + isNull() + ", IsEmpty = " + isEmpty() + ", IsSkip = " + isSkip() + ", IsCustom = "
        + isCustom() + ", Wert = null");
  }

}
