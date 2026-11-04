/*
 * @test
 * @summary Test HashSet constructor with initialCapacity
 * @run main HashSetWitupTest
 */

import java.util.HashSet;

public class HashSetWitupTest {
    public static void main(String[] args) {
        // Test with zero initial capacity
        HashSet<Object> setZero = new HashSet<>(0);
        if (setZero.size() != 0) {
            throw new RuntimeException("Expected empty set for initialCapacity=0");
        }

        // Test with positive initial capacity
        HashSet<String> setPositive = new HashSet<>(10);
        if (setPositive.size() != 0) {
            throw new RuntimeException("Expected empty set for initialCapacity=10");
        }

        // Test with large initial capacity
        HashSet<Integer> setLarge = new HashSet<>(1000);
        if (setLarge.size() != 0) {
            throw new RuntimeException("Expected empty set for initialCapacity=1000");
        }

        // Test with negative initial capacity - expect IllegalArgumentException
        boolean threw = false;
        try {
            new HashSet<>(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for negative initialCapacity");
        }
    }
}
