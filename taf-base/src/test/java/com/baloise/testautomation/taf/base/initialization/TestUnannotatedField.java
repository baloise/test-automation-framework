package com.baloise.testautomation.taf.base.initialization;

import com.baloise.testautomation.taf.base._base.ABase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestUnannotatedField extends ABase {

  private MockButtonImplementation field;

  @Test
  public void testInitializedByTaf() {
    assertNotNull(field);
  }

}
