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

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class ScreenshotRule extends TestWatcher {

  public static Logger logger = LoggerFactory.getLogger("ScreenshotOnFailed");

  private String path = "";
  private boolean onBefore = false;
  private boolean onAfter = false;
  private boolean onFailed = false;

  public ScreenshotRule(String path, boolean onBefore, boolean onAfter, boolean onFailed) {
    super();
    this.path = path;
    this.path = this.path.replace("\\", "/");
    if (this.path.length() > 0) {
      if (!this.path.endsWith("/")) {
        this.path = this.path + "/";
      }
    }
    this.onBefore = onBefore;
    this.onAfter = onAfter;
    this.onFailed = onFailed;
  }

  @Override
  protected void failed(Throwable e, Description description) {
    if (onFailed) {
      saveScreenShot("failed_" + getFilename(description));
    }
  }

  protected void finished(Description description) {
    if (onAfter) {
      saveScreenShot("after_" + getFilename(description));
    }
  }

  private String getFilename(Description description) {
    String methodName = description.getMethodName();
    methodName = String.format("%1.100s", methodName);    
    return System.currentTimeMillis() + "_" + methodName + ".jpg";
  }

  public void saveScreenShot() {
    saveScreenShot("screenshot_" + System.currentTimeMillis() + ".jpg");
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

  protected void starting(Description description) {
    if (onBefore) {
      saveScreenShot("before_" + getFilename(description));
    }
  }

}
