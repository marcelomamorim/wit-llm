/*
 * @test
 * @summary Test System.getProperty with valid and invalid keys
 * @run main SystemWitupTest
 */

import java.lang.System;
import java.util.Properties;

public class SystemWitupTest {

    public static void main(String[] args) {
        testGetPropertyExistingKey();
        testGetPropertyNonExistingKey();
        testGetPropertyNullKey();
    }

    static void testGetPropertyExistingKey() {
        String key = "java.version";
        String value = System.getProperty(key);
        if (value == null) {
            throw new AssertionError("Expected non-null value for key: " + key);
        }
        System.out.println("Existing key test passed: " + key + " = " + value);
    }

    static void testGetPropertyNonExistingKey() {
        String key = "non.existent.property.key";
        String value = System.getProperty(key);
        if (value != null) {
            throw new AssertionError("Expected null for non-existent key: " + key);
        }
        System.out.println("Non-existent key test passed: " + key);
    }

    static void testGetPropertyNullKey() {
        try {
            System.getProperty(null);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            System.out.println("Null key test passed: NullPointerException thrown as expected");
        }
    }
}
