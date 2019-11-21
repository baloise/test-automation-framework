package com.baloise.testautomation.taf.base.initialization;

import com.baloise.testautomation.taf.base._base.ABase;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestRuleAnnotatedField extends ABase {

    @Rule
    public final MockRuleImplementation field = new MockRuleImplementation("Test Rule Value");

    @Test
    public void testNotInitializedByTaf() {
        assertEquals(MockRuleImplementation.class, field.getClass());
        assertEquals("Test Rule Value", field.getValue());
    }

}
