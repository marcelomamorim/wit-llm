/*
 * @test
 * @summary Test for System.getProperty(String, String) method, focusing on exception paths.
 * @run main SystemWitupTest
 */

import java.lang.System;

public class SystemWitupTest {

    public static void main(String[] args) {
        testNullKey();
        testEmptyKey();
    }

    private static void testNullKey() {
        try {
            System.getProperty(null, "default");
            System.err.println("Expected NullPointerException for null key, but none was thrown.");
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException correctly thrown for null key.");
        } catch (Exception e) {
            System.err.println("Unexpected exception type for null key: " + e);
        }
    }

    private static void testEmptyKey() {
        try {
            System.getProperty("", "default");
            System.err.println("Expected IllegalArgumentException for empty key, but none was thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("IllegalArgumentException correctly thrown for empty key.");
        } catch (NullPointerException e) {
            System.err.println("Unexpected NullPointerException for empty key.");
        } catch (Exception e) {
            System.err.println("Unexpected exception type for empty key: " + e);
        }
    }
}
