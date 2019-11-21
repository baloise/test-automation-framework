package com.baloise.testautomation.taf.base.initialization;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class MockRuleImplementation implements TestRule {

    private final String value;

    public MockRuleImplementation() {
        throw new UnsupportedOperationException("This Default constructor should not be called.");
    }

    public MockRuleImplementation(String value) {
        this.value = value;
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                base.evaluate();
            }
        };
    }

    public String getValue() {
        return value;
    }

}
