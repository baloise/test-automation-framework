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
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class ResourceHelper {

  public static boolean tryWithAndWithoutPrefix = true;

  private static String dataRootPath = "";

  private static String resourcePrefix = "";

  private static Logger LOGGER = LoggerFactory.getLogger("resource-helper");

  public static URL getResource(Object o, String filename) {
    return getResource(o.getClass(), filename);
  }
  
  public static URL getResource(Class<?> klass, String filename) {
    LOGGER.info("determine URL for klass: " + klass + " --> dataRootPath: " + dataRootPath + " --> resourcePrefix: "
        + resourcePrefix + " --> filename: " + filename);
    if (dataRootPath.isEmpty()) {
      URL resource = klass.getResource(resourcePrefix + filename);
      if (resource == null) {
        if (tryWithAndWithoutPrefix) {
          LOGGER.info("resource with prefix does not exist --> try to find resource WITHOUT prefix");
          resource = klass.getResource(filename);
        }
      }
      return resource;
    }
    String oWithSlashes = klass.getPackage().getName().replace(".", "/");
    try {
      URL url = new URL(dataRootPath + oWithSlashes + "/" + resourcePrefix + filename);
      if (new File(url.toURI()).exists()) {
        return url;
      }
      if (tryWithAndWithoutPrefix) {
        LOGGER.info("resource with prefix does not exist --> try to find resource WITHOUT prefix");
        url = new URL(dataRootPath + oWithSlashes + "/" + filename);
        if (new File(url.toURI()).exists()) {
          return url;
        }
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
    if (!dataRootPath.isEmpty()) {
      Assert
          .assertTrue("data root must end with '\' or '/'", dataRootPath.endsWith("\\") || dataRootPath.endsWith("/"));
    }
    ResourceHelper.dataRootPath = dataRootPath;
  }

  public static void setResourcePrefix(String resourcePrefix) {
    Assert.assertNotNull("resource prefix cannot be null, use an empty string instead", resourcePrefix);
    ResourceHelper.resourcePrefix = resourcePrefix;
  }

}
