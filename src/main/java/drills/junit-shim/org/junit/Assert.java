package org.junit;

import java.util.Objects;

/**
 * Stand-in for org.junit.Assert with JUnit 4's overload set, so the drills in junit/
 * run without downloading JUnit. Anything that compiles against this compiles against
 * a real junit-4.13.2.jar.
 */
public class Assert {

    protected Assert() {
    }

    public static void fail() {
        fail(null);
    }

    public static void fail(String message) {
        throw message == null ? new AssertionError() : new AssertionError(message);
    }

    public static void assertTrue(boolean condition) {
        assertTrue(null, condition);
    }

    public static void assertTrue(String message, boolean condition) {
        if (!condition) fail(message);
    }

    public static void assertFalse(boolean condition) {
        assertFalse(null, condition);
    }

    public static void assertFalse(String message, boolean condition) {
        if (condition) fail(message);
    }

    public static void assertNull(Object actual) {
        assertNull(null, actual);
    }

    public static void assertNull(String message, Object actual) {
        if (actual != null) failNotEquals(message, null, actual);
    }

    public static void assertNotNull(Object actual) {
        assertNotNull(null, actual);
    }

    public static void assertNotNull(String message, Object actual) {
        assertTrue(message, actual != null);
    }

    public static void assertSame(Object expected, Object actual) {
        assertSame(null, expected, actual);
    }

    public static void assertSame(String message, Object expected, Object actual) {
        if (expected != actual) failNotEquals(message, expected, actual);
    }

    public static void assertNotSame(String message, Object unexpected, Object actual) {
        if (unexpected == actual) fail(format(message, "expected not same"));
    }

    public static void assertEquals(Object expected, Object actual) {
        assertEquals(null, expected, actual);
    }

    public static void assertEquals(String message, Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) failNotEquals(message, expected, actual);
    }

    public static void assertEquals(long expected, long actual) {
        assertEquals(null, expected, actual);
    }

    public static void assertEquals(String message, long expected, long actual) {
        if (expected != actual) failNotEquals(message, expected, actual);
    }

    public static void assertEquals(double expected, double actual, double delta) {
        assertEquals(null, expected, actual, delta);
    }

    public static void assertEquals(String message, double expected, double actual, double delta) {
        if (Double.compare(expected, actual) == 0) return;
        if (!(Math.abs(expected - actual) <= delta)) failNotEquals(message, expected, actual);
    }

    public static void assertNotEquals(Object unexpected, Object actual) {
        if (Objects.equals(unexpected, actual)) {
            fail("expected not equal to <" + unexpected + ">");
        }
    }

    public static void assertArrayEquals(Object[] expected, Object[] actual) {
        if (!java.util.Arrays.deepEquals(expected, actual)) {
            failNotEquals(null, java.util.Arrays.toString(expected), java.util.Arrays.toString(actual));
        }
    }

    private static void failNotEquals(String message, Object expected, Object actual) {
        fail(format(message, "expected:<" + expected + "> but was:<" + actual + ">"));
    }

    private static String format(String message, String body) {
        return message == null || message.isEmpty() ? body : message + " " + body;
    }
}
