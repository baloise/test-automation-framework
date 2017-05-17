package com.baloise.testautomation.taf.common.utils;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

public class TafProperties implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private Hashtable<String, Object> props = new Hashtable<String, Object>();

  public TafProperties() {
  }
  
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof TafProperties)) {
      return false;
    }
    return this.getProps().equals(((TafProperties)other).getProps());
  }
  
  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + props.hashCode();
    return result;
    
  }
  
  public TafProperties(TafProperties propsToCopy) {
    props = new Hashtable<String, Object>(propsToCopy.props);
  }
  
  public void clear() {
    props.clear();
  }

  public boolean getBoolean(String key) {
    Object o = getObject(key);
    if (o == null) {
      return false;
    }
    try {
      return Boolean.parseBoolean(o.toString());
    }
    catch (Exception e) {}
    return false;
  }

  public Class<?> getClass(String key) {
    Object o = getObject(key);
    if (o == null) {
      return null;
    }
    return o.getClass();
  }

  public Long getLong(String key) {
    Object o = getObject(key);
    if (o == null) {
      return null;
    }
    try {
      return Long.parseLong(o.toString());
    }
    catch (Exception e) {}
    return null;
  }

  public Object getObject(String key) {
    return props.get(key);
  }

  public String getString(String key) {
    Object o = getObject(key);
    if (o == null) {
      return null;
    }
    return o.toString();
  }
  
  private Hashtable<String, Object> getProps() {
    return props;
  }

  public Enumeration<String> keys() {
    return props.keys();
  }

  public Set<String> keySet() {
    return props.keySet();
  }

  public void putObject(String key, Object value) {
    if (key == null || value == null) {
      return;
    }
    props.put(key, value);
  }

  @Override
  public String toString() {
    String result = "";
    for (String s : keySet()) {
      result = result + s + " = " + getString(s) + " ";
    }
    return result.trim();
  }
}
