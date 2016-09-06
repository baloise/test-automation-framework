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

  private static String resourcePraefix = "";

  private static Logger LOGGER = LogManager.getLogger("resource-helper");

  public static URL getResource(Object o, String filename) {
    LOGGER.info("determine URL for object: " + o + " --> " + dataRootPath + resourcePraefix + " --> " + filename);
    if (dataRootPath.isEmpty()) {
      return o.getClass().getResource(resourcePraefix + filename);
    }
    String oWithSlashes = o.getClass().getPackage().getName().replace(".", "/");
    try {
      URL url = new URL(dataRootPath + oWithSlashes + "/" + resourcePraefix + filename);
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

  public static void setResourcePraefix(String resourcePraefix) {
    Assert.assertNotNull("resource praefix cannot be null, use an empty string instead", resourcePraefix);
    ResourceHelper.resourcePraefix = resourcePraefix;
  }

}
