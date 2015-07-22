/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.common.utils;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

/**
 * 
 */
public class TafProperties {
  
  private Hashtable<String, Object> props = new Hashtable<String, Object>();
  
  public void clear() {
    props.clear();
  }
  
  public Class<?> getClass(String key) {
    Object o = getObject(key);
    if (o == null) {
      return null;
    }
    return o.getClass();
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
  
  public Long getLong(String key) {
    Object o = getObject(key);
    if (o == null) {
      return null;
    }
    try {
      return Long.parseLong(o.toString());
    }
    catch (Exception e) {
    }
    return null;
  }
  
  public boolean getBoolean(String key) {
    Object o = getObject(key);
    if (o == null) {
      return false;
    }
    try {
      return Boolean.parseBoolean(o.toString());
    }
    catch (Exception e) {
    }
    return false;
  }
  
  public void putObject(String key, Object value) {
    if (key == null || value == null) {
      return;
    }
    props.put(key, value);
  }
  
  public Set<String> keySet() {
    return props.keySet();
  }
  
  public Enumeration<String> keys() {
    return props.keys();
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
