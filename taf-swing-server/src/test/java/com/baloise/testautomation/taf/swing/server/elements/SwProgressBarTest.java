package com.baloise.testautomation.taf.swing.server.elements;

import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.assertEquals;

public class SwProgressBarTest {

    @Test
    public void test() {
        JProgressBar component = new JProgressBar();
        JInternalFrame parent = new JInternalFrame();
        parent.add(component);
        component.setName("ComponentUnderTest");
        SwProgressBar swElement = new SwProgressBar(100, component);
        component.setMinimum(1);
        component.setMaximum(10);
        component.setValue(7);
        assertEquals(1, swElement.getMinimum());
        assertEquals(10, swElement.getMaximum());
        assertEquals(7, swElement.getValue());
    }

}
