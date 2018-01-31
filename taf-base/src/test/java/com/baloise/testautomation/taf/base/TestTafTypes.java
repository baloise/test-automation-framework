/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.junit.Test;

import com.baloise.testautomation.taf.base.types.TafComboString;
import com.baloise.testautomation.taf.base.types.TafInteger;
import com.baloise.testautomation.taf.base.types.TafString;

/**
 * 
 */
public class TestTafTypes {

  @Test
  public void tafComboString() {
    TafComboString cs = TafComboString.normalString("{true}");
    assertEquals("{true}", cs.asString());
    assertTrue(cs.asTafBoolean().asBoolean());
    assertTrue(cs.asTafInteger().isNull());
    assertTrue(cs.asTafDouble().isNull());
    assertTrue(cs.asTafDate().isNull());
    cs = TafComboString.normalString("{Skip}");
    assertTrue(cs.isSkip());
    assertEquals(null, cs.asString());
  }

  @Test
  public void tafInteger() {
    TafInteger i = TafInteger.normalInteger(111);
    assertEquals(new Integer(111), i.asTafInteger().asInteger());
    assertEquals("111", i.asTafString().asString());
    assertEquals(new Double(111.0), i.asTafDouble().asDouble());
    assertTrue(i.asTafDate().isNull());
    assertTrue(i.asTafBoolean().isNull());
    i = TafInteger.skipInteger();
    assertTrue(i.asTafString().isSkip());
    assertTrue(i.asTafDate().isSkip());
    assertTrue(i.asTafDouble().isSkip());
    assertTrue(i.asTafInteger().isSkip());
    i = new TafInteger("124");
    assertEquals(i.asInteger(), new Integer(124));
    i = new TafInteger("124" + DecimalFormatSymbols.getInstance().getDecimalSeparator() + "49");
    assertEquals(new Integer(124), i.asInteger());
    i = new TafInteger("124" + DecimalFormatSymbols.getInstance().getDecimalSeparator() +"51");
    assertEquals(new Integer(125), i.asInteger());
    try {
      i = new TafInteger("abc");
      fail();
    }
    catch (Throwable e) {}

  }

  @Test
  public void tafString() {
    TafString s = TafString.normalString("{true}");
    assertEquals("{true}", s.asString());
    assertTrue(s.asTafBoolean().asBoolean());
    assertTrue(s.asTafInteger().isNull());
    assertTrue(s.asTafDouble().isNull());
    assertTrue(s.asTafDate().isNull());
    s = TafString.normalString("21.02.2000");
    assertTrue(s.asTafInteger().isNull());
    assertTrue(s.asTafDouble().isNull());
    assertEquals("21.02.2000", s.asTafDate().asString());
    s = TafString.skipString();
    assertTrue(s.asTafString().isSkip());
    assertTrue(s.asTafDate().isSkip());
    assertTrue(s.asTafDouble().isSkip());
    assertTrue(s.asTafInteger().isSkip());
    s = new TafString("{sKip}");
    assertTrue(s.isSkip());
    assertEquals(null, s.asString());
  }
}
