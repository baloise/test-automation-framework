package com.baloise.testautomation.taf.base.types;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.baloise.testautomation.taf.base._interfaces.IType;

public class TafDate extends TafType implements IType {

  public static String dateFormat = "dd.MM.yyyy";

  public static TafDate emptyDate() {
    TafDate date = nullDate();
    date.setIsEmpty(true);
    return date;
  }

  public static TafDate normalDate(Date date) {
    if (date != null) {
      return new TafDate(date);
    }
    return nullDate();
  }

  public static TafDate normalDate(String date) {
    if (date == null) {
      return nullDate();
    }
    if (date.trim().isEmpty()) {
      return emptyDate();
    }
    if (date.trim().equalsIgnoreCase(EMPTY)) {
      return emptyDate();
    }
    if (date.trim().equalsIgnoreCase(NULL)) {
      return nullDate();
    }
    if (date.trim().equalsIgnoreCase(TODAY)) {
      return normalDate(new Date());
    }
    try {
      SimpleDateFormat df = new SimpleDateFormat(dateFormat);
      Date d = df.parse(date);
      return normalDate(d);
    }
    catch (Exception e) {}
    return customOrNullDate(date);
  }

  public static TafDate customOrNullDate(String date) {
    String custom = getCustom(date);
    if (custom != null) {
      TafDate d = new TafDate();
      d.setIsCustom(true);
      d.value = date;
      return d;
    }
    return nullDate();
  }
  public static TafDate nullDate() {
    return new TafDate((Date)null);
  }

  public static TafDate parameterDate(String parameterName) {
    TafDate result = TafDate.nullDate();
    result.setParameterName(parameterName);
    return result;
  }

  public TafDate() {
    super();
    this.value = null;
  }

  public TafDate(Date date) {
    super();
    this.value = date;
  }

  public TafDate(String date) {
    super();
    this.value = normalDate(date).value;
  }

  @Override
  public BigDecimal asBigDecimal() {
    return null;
  }

  @Override
  public Boolean asBoolean() {
    return null;
  }

  @Override
  public Date asDate() {
    if (isCustom()) {
      return null;
    }
    return (Date)value;
  }

  @Override
  public Double asDouble() {
    return null;
  }

  @Override
  public Integer asInteger() {
    return null;
  }

  @Override
  public Long asLong() {
    return null;
  }

  @Override
  public String asString() {
    if (isEmpty()) {
      return "";
    }
    if (isNull()) {
      return null;
    }
    if (isSkip()) {
      return null;
    }
    if (isCustom()) {
      return CUSTOM + value;
    }
    SimpleDateFormat df = new SimpleDateFormat(dateFormat);
    return df.format(value);
  }

  @Override
  public void basicSet(String s) {
    value = normalDate(s).value;
  }

  public void setValue(Date d) {
    value = d;
  }

}
