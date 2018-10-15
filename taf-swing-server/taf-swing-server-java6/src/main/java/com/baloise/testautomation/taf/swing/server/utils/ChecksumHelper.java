package com.baloise.testautomation.taf.swing.server.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChecksumHelper {

  public String getChecksum(InputStream input, String algorithm) throws NoSuchAlgorithmException, IOException {
    StringBuilder stringBuilder = new StringBuilder();
    MessageDigest digest;

    digest = MessageDigest.getInstance(algorithm);
    DigestInputStream digestInputStream = new DigestInputStream(input, digest);
    byte[] buffer = new byte[1024];
    try {
      while (digestInputStream.read(buffer) != -1);
    }
    finally {
      digestInputStream.close();
    }

    byte[] checksumBytes = digest.digest();

    for (int i = 0; i < checksumBytes.length; i++) {
      stringBuilder.append(Integer.toString((checksumBytes[i] & 0xff) + 0x100, 16).substring(1));
    }

    return stringBuilder.toString();
  }

}
