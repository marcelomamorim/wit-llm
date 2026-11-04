/*
 * @test
 * @summary Test java.util.Hashtable constructor with initialCapacity and loadFactor
 * @run main java.util.HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testNegativeInitialCapacity();
        testZeroInitialCapacity();
        testNegativeLoadFactor();
        testZeroLoadFactor();
        testNaNLoadFactor();
        testValidParameters();
    }

    private static void testNegativeInitialCapacity() {
        try {
            new Hashtable(-1, 0.75f);
            throw new RuntimeException("Expected IllegalArgumentException for negative initialCapacity");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testZeroInitialCapacity() {
        Hashtable<?, ?> ht = new Hashtable<>(0, 0.75f);
        if (ht == null) {
            throw new RuntimeException("Hashtable instance is null for zero initialCapacity");
        }
    }

    private static void testNegativeLoadFactor() {
        try {
            new Hashtable(10, -0.1f);
            throw new RuntimeException("Expected IllegalArgumentException for negative loadFactor");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testZeroLoadFactor() {
        try {
            new Hashtable(10, 0.0f);
            throw new RuntimeException("Expected IllegalArgumentException for zero loadFactor");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNaNLoadFactor() {
        try {
            new Hashtable(10, Float.NaN);
            throw new RuntimeException("Expected IllegalArgumentException for NaN loadFactor");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testValidParameters() {
        Hashtable<String, String> ht = new Hashtable<>(10, 0.75f);
        if (ht == null) {
            throw new RuntimeException("Hashtable instance is null for valid parameters");
        }
        ht.put("key", "value");
        String val = ht.get("key");
        if (!"value".equals(val)) {
            throw new RuntimeException("Hashtable get/put failed");
        }
    }
}
