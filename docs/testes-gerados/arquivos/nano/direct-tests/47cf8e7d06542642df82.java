/*
 * @test
 * @summary Test System.setProperty for setting a property and verifying its value.
 * @run main SystemWitupTest
 */

import java.lang.System;
import java.util.Properties;

public class SystemWitupTest {
    public static void main(String[] args) {
        String key = "test.property";
        String value = "testValue";
        String previousValue = System.setProperty(key, value);
        String currentValue = System.getProperty(key);
        if (!value.equals(currentValue)) {
            throw new AssertionError("Expected property value: " + value + ", but got: " + currentValue);
        }
        if (previousValue != null && previousValue.equals(value)) {
            // previous value was same as new value, acceptable
        }
        // Clean up: remove the property after test
        System.clearProperty(key);
        if (System.getProperty(key) != null) {
            throw new AssertionError("Property was not cleared properly.");
        }
    }
}
