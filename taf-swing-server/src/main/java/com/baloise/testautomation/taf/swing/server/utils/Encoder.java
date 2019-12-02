/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.server.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * 
 */
public class Encoder {

  public final static String ESCAPE_CHARS = "<>&\"\'";
  public final static List<String> ESCAPE_STRINGS = Collections.unmodifiableList(Arrays.asList(new String[] {"&lt;",
      "&gt;", "&amp;", "&quot;", "&apos;"}));

  private static String UNICODE_LOW = "" + ((char)0x20); // space
  private static String UNICODE_HIGH = "" + ((char)0x7f);

  public static String asEscapedString(String content) {
    String result = content;

    if ((content != null) && (content.length() > 0)) {
      boolean modified = false;
      StringBuilder stringBuilder = new StringBuilder(content.length());
      for (int i = 0, count = content.length(); i < count; ++i) {
        String character = content.substring(i, i + 1);
        int pos = ESCAPE_CHARS.indexOf(character);
        if (pos > -1) {
          stringBuilder.append(ESCAPE_STRINGS.get(pos));
          modified = true;
        }
        else {
          if ((character.compareTo(UNICODE_LOW) > -1) && (character.compareTo(UNICODE_HIGH) < 1)) {
            stringBuilder.append(character);
          }
          else {
            stringBuilder.append("&#" + ((int)character.charAt(0)) + ";");
            modified = true;
          }
        }
      }
      if (modified) {
        result = stringBuilder.toString();
      }
    }

    return result;
  }
  
  public static String asEscapedXmlString(String content) {
    return StringEscapeUtils.escapeXml10(content);
  }

}
