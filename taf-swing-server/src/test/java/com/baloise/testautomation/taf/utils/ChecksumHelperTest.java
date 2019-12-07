package com.baloise.testautomation.taf.utils;

import com.baloise.testautomation.taf.swing.server.utils.ChecksumHelper;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChecksumHelperTest {

  @Test
  public void testMd5Checksum() throws IOException, NoSuchAlgorithmException {
    ChecksumHelper checksumHelper = new ChecksumHelper();
    String checksum = checksumHelper.getChecksum(new ByteArrayInputStream("test".getBytes()), "MD5");
    assertEquals("098f6bcd4621d373cade4e832627b4f6", checksum);
  }

  @Test
  public void testSha1Checksum() throws IOException, NoSuchAlgorithmException {
    ChecksumHelper checksumHelper = new ChecksumHelper();
    String checksum = checksumHelper.getChecksum(new ByteArrayInputStream("test".getBytes()), "SHA-1");
    assertEquals("a94a8fe5ccb19ba61c4c0873d391e987982fbbd3", checksum);
  }

  @Test
  public void testNoSuchChecksumAlgorithm() {
    ChecksumHelper checksumHelper = new ChecksumHelper();
    assertThrows(NoSuchAlgorithmException.class,
                 () -> checksumHelper.getChecksum(new ByteArrayInputStream("".getBytes()), "bogus"));
  }

}
