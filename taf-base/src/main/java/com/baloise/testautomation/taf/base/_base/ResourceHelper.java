/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.base._base;

import java.io.File;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

/**
 * 
 */
public class ResourceHelper {

  private static String dataRootPath = "";

  private static Logger LOGGER = LogManager.getLogger("resource-helper");

  public static URL getResource(Object o, String filename) {
    LOGGER.info("determine resource URL for object: " + o + " --> dataroot: " + dataRootPath + " --> filename: "
        + filename);
    if (dataRootPath.isEmpty()) {
      return o.getClass().getResource(filename);
    }
    String oWithSlashes = o.getClass().getPackage().getName().replace(".", "/");
    try {
      URL url = new URL(dataRootPath + oWithSlashes + "/" + filename);
      if (new File(url.toURI()).exists()) {
        return url;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    LOGGER.error("resource url not determinable");
    return null;
  }

  public static void setDataRootPath(String dataRootPath) {
    Assert.assertNotNull("data root cannot be null, use an empty string instead", dataRootPath);
    ResourceHelper.dataRootPath = dataRootPath;
  }

}
