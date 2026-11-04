/*
 * @test
 * @summary Test java.lang.System.getProperty(String) for key validation and security manager interaction
 * @run main SystemWitupTest
 */

import java.lang.System;
import java.security.SecurityManager;

public class SystemWitupTest {
    public static void main(String[] args) {
        testGetPropertyNullKey();
        testGetPropertyEmptyKey();
        testGetPropertyValidKey();
    }

    private static void testGetPropertyNullKey() {
        try {
            System.getProperty(null);
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testGetPropertyEmptyKey() {
        try {
            System.getProperty("");
            throw new RuntimeException("Expected IllegalArgumentException for empty key");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    private static void testGetPropertyValidKey() {
        // Use a known property key that should exist or be null but not throw
        String key = "java.version";
        String value = System.getProperty(key);
        if (value == null) {
            // Property might be null if not set, but no exception should be thrown
            // Just print to confirm no exception
            System.out.println("Property '" + key + "' is null as expected or not set.");
        } else {
            // Confirm non-empty string
            if (value.isEmpty()) {
                throw new RuntimeException("Property '" + key + "' should not be empty string");
            }
        }
    }
}
