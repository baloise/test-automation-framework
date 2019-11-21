package com.baloise.testautomation.taf.base.initialization;

import com.baloise.testautomation.taf.base._base.ABase;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestByAnnotatedField extends ABase {

    private static final String BY_ANNOTATION_VALUE = "By Annotation Value";

    @IAnnotations.ByLeftLabel(BY_ANNOTATION_VALUE)
    private MockButtonImplementation field;

    @Test
    public void testInitializedByTaf() {
        assertNotNull(field);
        assertNotNull(field.getBy());
        assertEquals(IAnnotations.ByLeftLabel.class, field.getBy().annotationType());
        assertEquals(BY_ANNOTATION_VALUE, ((IAnnotations.ByLeftLabel) field.getBy()).value());
    }

}
