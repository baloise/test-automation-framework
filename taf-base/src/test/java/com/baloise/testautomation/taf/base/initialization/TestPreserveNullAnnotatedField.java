package com.baloise.testautomation.taf.base.initialization;

import com.baloise.testautomation.taf.base._base.ABase;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestPreserveNullAnnotatedField extends ABase {

    @IAnnotations.PreserveNull
    private String fieldWithPreserveNull;

    private String fieldWithoutPreserveNull;

    @Test
    public void testNotInitializedByTaf() {
        assertNull(fieldWithPreserveNull);
    }

    @Test
    public void testInitializedByTaf() {
        assertNotNull(fieldWithoutPreserveNull);
    }

}
