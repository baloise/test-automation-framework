package com.baloise.testautomation.taf.base.testing;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

public class ScreenshotRule implements TestWatcher, BeforeEachCallback, AfterEachCallback {

  public static Logger logger = LoggerFactory.getLogger("ScreenshotOnFailed");

  private String path = "";
  private boolean onBefore = false;
  private boolean onAfter = false;
  private boolean onFailed = false;
  private boolean withClassName = false;

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
  public void testFailed(ExtensionContext context, Throwable cause) {
    if (onFailed) {
      saveScreenShot("failed_" + getFilename(context));
    }
  }

  private String getFilename(ExtensionContext context) {
    String methodName = context.getTestMethod().get().getName();
    if (withClassName) {
      String[] splittedClassName = context.getTestClass().get().getName().split("\\.");
      if (splittedClassName.length > 0) {
        methodName = splittedClassName[splittedClassName.length - 1] + "_" + methodName;
      }
    }
    methodName = String.format("%1.100s", methodName);
    return System.currentTimeMillis() + "_" + methodName + ".png";
  }

  public String getPath() {
    return path;
  }

  public void saveScreenShot() {
    saveScreenShot("screenshot_" + System.currentTimeMillis() + ".png");
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
      ImageIO.write(image, "png", f);
      logger.info("Done saveScreenShot() " + f);
    }
    catch (Exception ex) {
      logger.error("Error when saving image: " + ex.getMessage());
    }
  }

  public ScreenshotRule withClassName() {
    this.withClassName = true;
    return this;
  }

  @Override
  public void beforeEach(ExtensionContext context) throws Exception {
    if (onBefore) {
      saveScreenShot("before_" + getFilename(context));
    }
  }

  @Override
  public void afterEach(ExtensionContext context) throws Exception {
    if (onAfter) {
      saveScreenShot("after_" + getFilename(context));
    }
  }

}
