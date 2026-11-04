/*
 * @test
 * @summary Test java.util.Hashtable constructor with initialCapacity and loadFactor
 * @run main HashtableWitupTest
 */

import java.util.Hashtable;

public class HashtableWitupTest {
    public static void main(String[] args) {
        testValidConstructor();
        testZeroInitialCapacity();
        testNegativeInitialCapacity();
        testZeroLoadFactor();
        testNegativeLoadFactor();
        testNaNLoadFactor();
    }

    private static void testValidConstructor() {
        Hashtable<Object, Object> ht = new Hashtable<>(10, 0.75f);
        if (ht == null) throw new RuntimeException("Hashtable instance is null");
    }

    private static void testZeroInitialCapacity() {
        Hashtable<Object, Object> ht = new Hashtable<>(0, 0.75f);
        if (ht == null) throw new RuntimeException("Hashtable instance is null");
    }

    private static void testNegativeInitialCapacity() {
        try {
            new Hashtable<>(-1, 0.75f);
            throw new RuntimeException("Expected IllegalArgumentException for negative initialCapacity");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testZeroLoadFactor() {
        try {
            new Hashtable<>(10, 0.0f);
            throw new RuntimeException("Expected IllegalArgumentException for zero loadFactor");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNegativeLoadFactor() {
        try {
            new Hashtable<>(10, -0.1f);
            throw new RuntimeException("Expected IllegalArgumentException for negative loadFactor");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNaNLoadFactor() {
        try {
            new Hashtable<>(10, Float.NaN);
            throw new RuntimeException("Expected IllegalArgumentException for NaN loadFactor");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
