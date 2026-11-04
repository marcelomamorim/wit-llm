/*
 * @test
 * @summary Test java.lang.System.getLogger(String, ResourceBundle) behavior
 * @run main SystemWitupTest
 */

import java.lang.System;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.Objects;

public class SystemWitupTest {
    public static void main(String[] args) throws Exception {
        testNullNameThrowsNPE();
        testNullBundleThrowsNPE();
        testValidLoggerReturned();
        testIllegalCallerExceptionIfNoCaller();
    }

    private static void testNullNameThrowsNPE() {
        ResourceBundle rb = ResourceBundle.getBundle("java.util.logging.LogManagerResources");
        try {
            System.getLogger(null, rb);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testNullBundleThrowsNPE() {
        try {
            System.getLogger("TestLogger", null);
            throw new RuntimeException("Expected NullPointerException for null bundle");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testValidLoggerReturned() {
        ResourceBundle rb = ResourceBundle.getBundle("java.util.logging.LogManagerResources");
        String loggerName = "TestLogger";
        Logger logger = System.getLogger(loggerName, rb);
        if (logger == null) {
            throw new RuntimeException("Logger returned by getLogger should not be null");
        }
        if (!logger.getName().equals(loggerName)) {
            throw new RuntimeException("Logger name mismatch: expected " + loggerName + " but got " + logger.getName());
        }
    }

    private static void testIllegalCallerExceptionIfNoCaller() throws Exception {
        // This test attempts to simulate the condition where Reflection.getCallerClass() returns null.
        // Since Reflection.getCallerClass() is internal and cannot be mocked here, we test indirectly by
        // invoking getLogger via reflection from a different thread with no caller frame.
        // This is a best-effort test to detect IllegalCallerException.

        java.lang.reflect.Method getLoggerMethod = System.class.getDeclaredMethod("getLogger", String.class, ResourceBundle.class);
        getLoggerMethod.setAccessible(true);

        final ResourceBundle rb = ResourceBundle.getBundle("java.util.logging.LogManagerResources");

        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    getLoggerMethod.invoke(null, "TestLogger", rb);
                    throw new RuntimeException("Expected IllegalCallerException not thrown");
                } catch (java.lang.reflect.InvocationTargetException ite) {
                    Throwable cause = ite.getCause();
                    if (!(cause instanceof IllegalCallerException)) {
                        throw new RuntimeException("Expected IllegalCallerException but got " + cause, cause);
                    }
                    // expected
                } catch (Exception e) {
                    throw new RuntimeException("Unexpected exception", e);
                }
            }
        });

        t.start();
        t.join();
    }
}
