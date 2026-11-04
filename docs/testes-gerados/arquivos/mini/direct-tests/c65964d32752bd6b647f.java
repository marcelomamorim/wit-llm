/*
 * @test
 * @summary Test HashMap constructor with initialCapacity and loadFactor
 * @run main HashMapWitupTest
 */

import java.util.HashMap;

public class HashMapWitupTest {
    public static void main(String[] args) {
        testNegativeInitialCapacity();
        testInitialCapacityAboveMaximum();
        testZeroLoadFactor();
        testNegativeLoadFactor();
        testNaNLoadFactor();
        testValidParameters();
    }

    static void testNegativeInitialCapacity() {
        try {
            new HashMap(-1, 0.75f);
            throw new RuntimeException("Expected IllegalArgumentException for negative initialCapacity");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInitialCapacityAboveMaximum() {
        // The constructor caps initialCapacity to MAXIMUM_CAPACITY internally
        // We cannot access threshold or MAXIMUM_CAPACITY directly, so test no exception
        // and that map can be created with large initialCapacity
        HashMap<Object, Object> map = new HashMap<>(Integer.MAX_VALUE, 0.75f);
        if (map == null) {
            throw new RuntimeException("HashMap constructor returned null");
        }
    }

    static void testZeroLoadFactor() {
        try {
            new HashMap(16, 0.0f);
            throw new RuntimeException("Expected IllegalArgumentException for zero loadFactor");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testNegativeLoadFactor() {
        try {
            new HashMap(16, -0.1f);
            throw new RuntimeException("Expected IllegalArgumentException for negative loadFactor");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testNaNLoadFactor() {
        try {
            new HashMap(16, Float.NaN);
            throw new RuntimeException("Expected IllegalArgumentException for NaN loadFactor");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testValidParameters() {
        HashMap<Object, Object> map = new HashMap<>(16, 0.75f);
        if (map == null) {
            throw new RuntimeException("HashMap constructor returned null for valid parameters");
        }
    }
}
