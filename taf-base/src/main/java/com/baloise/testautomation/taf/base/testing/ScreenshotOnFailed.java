/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.base.testing;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * 
 */
public class ScreenshotOnFailed extends TestWatcher {

  public static Logger logger = LogManager.getLogger("ScreenshotOnFailed");

  private String path = "";

  public ScreenshotOnFailed(String path) {
    super();
    this.path = path;
    this.path = this.path.replace("\\", "/");
    if (this.path.length() > 0) {
      if (!this.path.endsWith("/")) {
        this.path = this.path + "/";
      }
    }
  }

  @Override
  protected void failed(Throwable e, Description description) {
    saveScreenShot("failed_" + System.currentTimeMillis() + "_" + description.getMethodName() + ".jpg");
  }

  public void saveScreenShot() {
    saveScreenShot("failed_" + System.currentTimeMillis() + ".jpg");
  }

  public void saveScreenShot(String filename) {
    logger.info("Execute saveScreenShot()");
    try {
      Toolkit toolkit = Toolkit.getDefaultToolkit(); // Toolkit class returns the default toolkit
      Dimension dimension = toolkit.getScreenSize();
      Rectangle rectangle = new Rectangle(0, 0, dimension.width, dimension.height);
      Robot robot = new Robot();
      BufferedImage image = robot.createScreenCapture(rectangle);
      File f = new File(path + filename);
      ImageIO.write(image, "jpg", f);
      logger.info("Done saveScreenShot() " + f);
    }
    catch (Exception ex) {
      logger.error("Error when saving image: " + ex.getMessage());
    }
  }

}
