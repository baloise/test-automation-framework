package com.baloise.testautomation.taf.base._base;

import java.util.Objects;

public class TafAssert {

    public static void fail(String message) {
        throw new TafError(message);
    }

    public static void assertTrue(String message, boolean bool) {
        if (!bool) {
            fail(message);
        }
    }

    public static void assertNotNull(String message, Object object) {
        if (object == null) {
            fail(message);
        }
    }

    public static void assertFalse(String message, boolean bool) {
        assertTrue(message, !bool);
    }

    public static void assertEquals(String message, Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            fail(format(message, expected, actual));
        }
    }

    public static void assertEquals(Object expected, Object actual) {
        assertEquals(null, expected, actual);
    }

    private static String format(String message, Object expected, Object actual) {
        String formatted = "";
        if (message != null && message.length() > 0) {
            formatted = message + " ";
        }
        return formatted + "expected:<" + expected + "> but was:<" + actual + ">";
    }

}
