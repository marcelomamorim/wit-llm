/*
 * @test
 * @summary Tests the System.log method for various inputs.
 * @run main SystemWitupTest
 */

import java.lang.System;
import java.util.logging.Level;

public class SystemWitupTest {

    public static void main(String[] args) {
        testLogWithNullLevel();
        testLogWithEmptyMessage();
        testLogWithNullThrowable();
        testLogWithValidLevelAndMessage();
    }

    static void testLogWithNullLevel() {
        try {
            System.log(null, "Test message", null);
            System.out.println("testLogWithNullLevel passed");
        } catch (Exception e) {
            System.err.println("testLogWithNullLevel failed: " + e);
        }
    }

    static void testLogWithEmptyMessage() {
        try {
            System.log(Level.INFO, "", null);
            System.out.println("testLogWithEmptyMessage passed");
        } catch (Exception e) {
            System.err.println("testLogWithEmptyMessage failed: " + e);
        }
    }

    static void testLogWithNullThrowable() {
        try {
            System.log(Level.WARNING, "Warning message", null);
            System.out.println("testLogWithNullThrowable passed");
        } catch (Exception e) {
            System.err.println("testLogWithNullThrowable failed: " + e);
        }
    }

    static void testLogWithValidLevelAndMessage() {
        try {
            System.log(Level.SEVERE, "Severe error occurred", new RuntimeException("Test exception"));
            System.out.println("testLogWithValidLevelAndMessage passed");
        } catch (Exception e) {
            System.err.println("testLogWithValidLevelAndMessage failed: " + e);
        }
    }
}
