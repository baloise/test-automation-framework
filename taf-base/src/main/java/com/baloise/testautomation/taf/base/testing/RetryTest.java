/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.base.testing;

import java.util.Hashtable;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.AssumptionViolatedException;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * 
 */
public class RetryTest implements TestRule {

  private static Hashtable<String, Integer> retriedMethods = new Hashtable<String, Integer>();
  public static Logger logger = LogManager.getLogger("RetryTest");
  public static boolean doNotRetryOnAssumptionViolatedException = true;

  public static void addRetry(Description description, Integer count) {
    retriedMethods.put(description.toString(), count);
  }

  public static void clearRetriedMethods() {
    retriedMethods = new Hashtable<String, Integer>();
  }

  public static void logRetriedMethods() {
    Set<String> keys = retriedMethods.keySet();
    if (keys.size() > 0) {
      logger.info("--- Retried methods ---");
      for (String key : keys) {
        logger.info(key + ": " + retriedMethods.get(key));
      }
    }
  }

  private int retryCount;

  private boolean wasLastRetry = false;

  public RetryTest(int retryCount) {
    this.retryCount = retryCount;
  }

  public Statement apply(Statement base, Description description) {
    return statement(base, description);
  }

  private Statement statement(final Statement base, final Description description) {
    return new Statement() {

      private int actualRetries = 0;

      @Override
      public void evaluate() throws Throwable {
        wasLastRetry = false;
        Throwable caughtThrowable = null;

        // implement retry logic here
        for (int i = 0; i < retryCount; i++) {
          actualRetries++;
          try {
            base.evaluate();
            wasLastRetry = true;
            return;
          }
          catch (Throwable t) {
            caughtThrowable = t;
            if (doNotRetryOnAssumptionViolatedException) {
              if (AssumptionViolatedException.class.equals(t.getClass())) {
                logger.info("No retry because assumption was violated");
                wasLastRetry = true;
                break;
              }
            }
            System.err.println(description.getDisplayName() + ": run " + (i + 1) + " failed");
            addRetry(description, i + 1);
            t.printStackTrace();
          }
        }
        System.err.println(description.getDisplayName() + ": giving up after " + actualRetries
            + " failures/assumption violated");
        wasLastRetry = true;
        throw caughtThrowable;
      }
    };
  }

  public boolean wasLastRetry() {
    return wasLastRetry;
  }

}
