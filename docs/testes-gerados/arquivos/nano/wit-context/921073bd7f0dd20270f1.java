/*
 * @test
 * @summary Test for System.getProperty(String key)
 * @run main SystemWitupTest
 */

import java.lang.System;

public class SystemWitupTest {
    public static void main(String[] args) {
        testGetPropertyWithNullKey();
        testGetPropertyWithEmptyKey();
        testGetPropertyWithValidKey();
    }

    private static void testGetPropertyWithNullKey() {
        try {
            String result = System.getProperty(null);
            // Expecting NullPointerException, so if no exception, test fails
            System.err.println("Expected NullPointerException for null key, but got result: " + result);
        } catch (NullPointerException e) {
            // Test passes
        } catch (Exception e) {
            System.err.println("Unexpected exception for null key: " + e);
        }
    }

    private static void testGetPropertyWithEmptyKey() {
        try {
            String result = System.getProperty("");
            // Expecting possibly null or empty string, depending on environment
            System.out.println("Result for empty key: " + result);
        } catch (Exception e) {
            System.err.println("Unexpected exception for empty key: " + e);
        }
    }

    private static void testGetPropertyWithValidKey() {
        try {
            String key = "java.version";
            String result = System.getProperty(key);
            if (result != null) {
                System.out.println("java.version: " + result);
            } else {
                System.out.println("Property 'java.version' not found.");
            }
        } catch (Exception e) {
            System.err.println("Unexpected exception for valid key: " + e);
        }
    }
}
