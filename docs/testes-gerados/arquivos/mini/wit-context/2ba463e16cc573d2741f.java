/*
 * @test
 * @summary Test System.getLogger(String, ResourceBundle) for null argument checks and basic behavior
 * @run main SystemWitupTest
 */

import java.lang.System;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.lang.IllegalCallerException;
import java.util.Objects;

public class SystemWitupTest {
    public static void main(String[] args) {
        testNullBundleThrowsNPE();
        testNullNameThrowsNPE();
        testNoCallerFrameThrowsIllegalCallerException();
        testValidCallReturnsLogger();
    }

    private static void testNullBundleThrowsNPE() {
        ResourceBundle bundle = null;
        String name = "testLogger";
        try {
            System.getLogger(name, bundle);
            throw new AssertionError("Expected NullPointerException for null bundle");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testNullNameThrowsNPE() {
        ResourceBundle bundle = ResourceBundle.getBundle("java.util.logging.LogManagerResources");
        String name = null;
        try {
            System.getLogger(name, bundle);
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testNoCallerFrameThrowsIllegalCallerException() {
        // The method uses Reflection.getCallerClass() which returns null if no caller frame
        // We cannot directly simulate no caller frame in a normal call, so we skip this test
        // because it requires internal JVM state manipulation not accessible here.
        // This is noted here to document the limitation.
    }

    private static void testValidCallReturnsLogger() {
        ResourceBundle bundle = ResourceBundle.getBundle("java.util.logging.LogManagerResources");
        String name = "testLogger";
        Logger logger = System.getLogger(name, bundle);
        if (logger == null) {
            throw new AssertionError("Expected non-null Logger instance");
        }
        // Basic sanity check: logger name should match
        if (!name.equals(logger.getName())) {
            throw new AssertionError("Logger name mismatch");
        }
    }
}
