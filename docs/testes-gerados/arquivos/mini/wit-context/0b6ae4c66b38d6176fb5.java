/*
 * @test
 * @summary Test IdentityHashMap(int expectedMaxSize) constructor for valid and invalid inputs
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;

public class IdentityHashMapWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidSize();
        testConstructorWithZeroSize();
        testConstructorWithNegativeSize();
    }

    private static void testConstructorWithValidSize() {
        // Test with a positive expectedMaxSize
        IdentityHashMap<Object, Object> map = new IdentityHashMap<>(10);
        if (map == null) {
            throw new AssertionError("Constructor returned null for positive size");
        }
    }

    private static void testConstructorWithZeroSize() {
        // Test with zero expectedMaxSize
        IdentityHashMap<Object, Object> map = new IdentityHashMap<>(0);
        if (map == null) {
            throw new AssertionError("Constructor returned null for zero size");
        }
    }

    private static void testConstructorWithNegativeSize() {
        // Test that constructor throws IllegalArgumentException for negative expectedMaxSize
        try {
            new IdentityHashMap<>(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative expectedMaxSize");
        } catch (IllegalArgumentException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("expectedMaxSize is negative")) {
                throw new AssertionError("Exception message does not contain expected text: " + msg);
            }
        }
    }
}
