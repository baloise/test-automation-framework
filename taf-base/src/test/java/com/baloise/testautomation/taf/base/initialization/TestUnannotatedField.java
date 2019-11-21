package com.baloise.testautomation.taf.base.initialization;

import com.baloise.testautomation.taf.base._base.ABase;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TestUnannotatedField extends ABase {

    private MockButtonImplementation field;

    @Test
    public void testInitializedByTaf() {
        assertNotNull(field);
    }

}
