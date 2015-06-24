package com.baloise.testautomation.taf.base.types;

import com.baloise.testautomation.taf.base._interfaces.IType;

public abstract class TafType implements IType {

  public static final String EMPTY = "{empty}";
  public static final String NULL = "{null}";
  public static final String SKIP = "{skip}";
  public static final String TODAY = "{today}";
  public static final String SETMARKER = "{set}";
  public static final String GETMARKER = "{get}";

  public static String GetGetMarker() {
    return GETMARKER;
  }

  public static String GetSetMarker() {
    return SETMARKER;
  }

  private String parameterName = "";

  protected boolean isEmpty = false;
  protected boolean isSkip = false;
  protected Object value = null;

  @Override
  public TafBoolean asTafBoolean() {
    TafBoolean result = TafBoolean.normalBoolean(asString());
    result.setIsEmpty(isEmpty());
    result.setIsSkip(isSkip());
    return result;
  }

  @Override
  public TafDate asTafDate() {
    TafDate result = TafDate.normalDate(asString());
    result.setIsEmpty(isEmpty());
    result.setIsSkip(isSkip());
    return result;
  }

  @Override
  public TafDouble asTafDouble() {
    TafDouble result = TafDouble.normalDouble(asString());
    result.setIsEmpty(isEmpty());
    result.setIsSkip(isSkip());
    return result;
  }

  @Override
  public TafInteger asTafInteger() {
    TafInteger result = TafInteger.normalInteger(asString());
    result.setIsEmpty(isEmpty());
    result.setIsSkip(isSkip());
    return result;
  }

  @Override
  public TafString asTafString() {
    TafString result = new TafString(asString());
    result.setIsEmpty(isEmpty());
    result.setIsSkip(isSkip());
    return result;
  }

  public abstract void basicSet(String s);

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
  public void set(String s) {
    setIsSkip(false);
    setIsEmpty(false);
    if (s == null) {
      value = s;
      return;
    }
    if (s.isEmpty()) {
      setIsEmpty(isEmpty);
    }
    if (SKIP.equalsIgnoreCase(s)) {
      setIsSkip(true);
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
  public void setParameterName(String parameterName) {
    this.parameterName = parameterName;
  }

  @Override
  public String toString() {
    if (value != null) {
      return ("IsNull = " + isNull() + ", IsEmpty = " + isEmpty() + ", IsSkip = " + isSkip() + ", Wert = " + value
          .toString());
    }
    return ("IsNull = " + isNull() + ", IsEmpty = " + isEmpty() + ", IsSkip = " + isSkip() + ", Wert = null");
  }

}
