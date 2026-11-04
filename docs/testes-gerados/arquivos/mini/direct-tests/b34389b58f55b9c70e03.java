/*
 * @test
 * @summary Test HashSet constructor with initialCapacity and loadFactor
 * @run main HashSetWitupTest
 */

import java.util.HashSet;
import java.util.HashMap;

public class HashSetWitupTest {
    public static void main(String[] args) {
        testConstructorBasic();
        testConstructorWithZeroCapacity();
        testConstructorWithLoadFactorOne();
        testConstructorWithLargeCapacityAndLoadFactor();
    }

    private static void testConstructorBasic() {
        HashSet<String> set = new HashSet<>(16, 0.75f);
        if (set.size() != 0) {
            throw new AssertionError("New HashSet should be empty");
        }
        // Add element and check contains
        set.add("test");
        if (!set.contains("test")) {
            throw new AssertionError("HashSet should contain added element");
        }
    }

    private static void testConstructorWithZeroCapacity() {
        // initialCapacity 0 is allowed by HashMap (it will be adjusted internally)
        HashSet<Integer> set = new HashSet<>(0, 0.75f);
        if (set.size() != 0) {
            throw new AssertionError("New HashSet with zero capacity should be empty");
        }
        set.add(1);
        if (!set.contains(1)) {
            throw new AssertionError("HashSet should contain added element");
        }
    }

    private static void testConstructorWithLoadFactorOne() {
        HashSet<Double> set = new HashSet<>(4, 1.0f);
        if (set.size() != 0) {
            throw new AssertionError("New HashSet should be empty");
        }
        set.add(3.14);
        if (!set.contains(3.14)) {
            throw new AssertionError("HashSet should contain added element");
        }
    }

    private static void testConstructorWithLargeCapacityAndLoadFactor() {
        HashSet<String> set = new HashSet<>(1000, 0.5f);
        if (set.size() != 0) {
            throw new AssertionError("New HashSet should be empty");
        }
        set.add("large");
        if (!set.contains("large")) {
            throw new AssertionError("HashSet should contain added element");
        }
    }
}
