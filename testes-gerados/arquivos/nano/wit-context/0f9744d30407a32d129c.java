/*
 * @test
 * @summary Test for System.clearProperty(String key) method.
 * @run main SystemWitupTest
 */

import java.lang.System;
import java.util.Properties;

public class SystemWitupTest {
    public static void main(String[] args) {
        testClearPropertyWithNullKey();
        testClearPropertyWithEmptyKey();
        testClearPropertyWithValidKey();
    }

    private static void testClearPropertyWithNullKey() {
        try {
            System.clearProperty(null);
            System.out.println("FAIL: Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null key");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for null key: " + t);
        }
    }

    private static void testClearPropertyWithEmptyKey() {
        Properties props = System.getProperties();
        String key = "";
        props.setProperty(key, "value");
        try {
            String result = System.clearProperty(key);
            if (result != null && result.equals("value")) {
                System.out.println("PASS: clearProperty with empty key returned previous value");
            } else {
                System.out.println("FAIL: clearProperty with empty key did not return expected value");
            }
            if (props.getProperty(key) == null) {
                System.out.println("PASS: Property with empty key removed from properties");
            } else {
                System.out.println("FAIL: Property with empty key still present after clearProperty");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for empty key: " + t);
        }
    }

    private static void testClearPropertyWithValidKey() {
        Properties props = System.getProperties();
        String key = "testKey";
        String value = "testValue";
        props.setProperty(key, value);
        String previous = System.getProperty(key);
        try {
            String result = System.clearProperty(key);
            if (result != null && result.equals(value)) {
                System.out.println("PASS: clearProperty returned previous value for valid key");
            } else {
                System.out.println("FAIL: clearProperty did not return expected previous value");
            }
            if (System.getProperty(key) == null) {
                System.out.println("PASS: Property with key '" + key + "' removed from properties");
            } else {
                System.out.println("FAIL: Property with key '" + key + "' still present after clearProperty");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for valid key: " + t);
        }
    }
}
