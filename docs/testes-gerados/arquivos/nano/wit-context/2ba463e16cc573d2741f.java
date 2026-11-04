/*
 * @test
 * @summary Tests for java.lang.System.getLogger
 * @run main SystemWitupTest
 */

import java.lang.System;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class SystemWitupTest {

    public static void main(String[] args) {
        testGetLoggerNullBundle();
        testGetLoggerNullName();
    }

    private static void testGetLoggerNullBundle() {
        String name = "TestLogger";
        ResourceBundle bundle = null;
        try {
            System.getLogger(name, bundle);
            // Expecting NullPointerException due to bundle == null
            System.err.println("Failed: Expected NullPointerException for null bundle");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null bundle");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }

    private static void testGetLoggerNullName() {
        String name = null;
        ResourceBundle bundle = ResourceBundle.getBundle("java.util.logging");
        try {
            System.getLogger(name, bundle);
            System.err.println("Failed: Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null name");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }
}
