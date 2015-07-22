package com.baloise.testautomation.taf.server;
import org.junit.Assert;
import org.junit.Test;

import com.baloise.testautomation.taf.swing.server.main.SwApplication;

/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */

/**
 * 
 */
public class SwApplicationTest {

    @Test
    public void formattedXMLToScreen() {
      SwApplication app = new SwApplication();
      String xml = app.storeFormatted("<frame><another>hello</another></frame>", null);
      Assert.assertEquals(true, !xml.isEmpty());
    }
}
