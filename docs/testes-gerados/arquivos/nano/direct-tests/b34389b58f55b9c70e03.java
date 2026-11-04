/* @test    @summary Test HashSet constructor with initial capacity and load factor    @run main HashSetWitupTest */

import java.util.HashSet;

public class HashSetWitupTest {

    public static void main(String[] args) {
        testHashSetConstructorWithCapacityAndLoadFactor();
        testHashSetConstructorWithZeroCapacity();
        testHashSetConstructorWithNegativeCapacity();
        testHashSetConstructorWithZeroLoadFactor();
        testHashSetConstructorWithNegativeLoadFactor();
    }

    static void testHashSetConstructorWithCapacityAndLoadFactor() {
        HashSet<Integer> set = new HashSet<>(10, 0.75f);
        if (set == null) {
            throw new AssertionError("HashSet with capacity 10 and load factor 0.75f should not be null");
        }
    }

    static void testHashSetConstructorWithZeroCapacity() {
        HashSet<Integer> set = new HashSet<>(0, 0.75f);
        if (set == null) {
            throw new AssertionError("HashSet with zero capacity should not be null");
        }
    }

    static void testHashSetConstructorWithNegativeCapacity() {
        try {
            new HashSet<>(-1, 0.75f);
            throw new AssertionError("Expected IllegalArgumentException for negative capacity");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testHashSetConstructorWithZeroLoadFactor() {
        try {
            new HashSet<>(10, 0.0f);
            throw new AssertionError("Expected IllegalArgumentException for zero load factor");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testHashSetConstructorWithNegativeLoadFactor() {
        try {
            new HashSet<>(10, -0.5f);
            throw new AssertionError("Expected IllegalArgumentException for negative load factor");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
