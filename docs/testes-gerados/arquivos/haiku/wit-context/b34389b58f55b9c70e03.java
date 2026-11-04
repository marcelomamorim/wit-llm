/*
 * @test
 * @summary WITUP-derived tests for HashSet(int initialCapacity, float loadFactor) constructor
 * @run main HashSetWitupTest
 */

import java.util.HashSet;

public class HashSetWitupTest {

    public static void main(String[] args) {
        testValidConstruction();
        testInvalidInitialCapacity();
        testInvalidLoadFactor();
        testLargeCapacityWithInvalidLoadFactor();
        testMaximumCapacityConstructionDoesNotThrow();
    }

    static void testValidConstruction() {
        HashSet<String> s = new HashSet<>(0, 0.75f);
        check(s.isEmpty(), "new set with capacity 0 should be empty");
        check(s.size() == 0, "size should be 0");
        s.add("a");
        s.add(null);
        check(s.size() == 2, "set should accept null and non-null elements");
        check(s.contains(null), "set should contain null");
        check(s.contains("a"), "set should contain \"a\"");

        HashSet<Integer> t = new HashSet<>(16, 10f);
        for (int i = 0; i < 1000; i++) {
            t.add(i);
        }
        check(t.size() == 1000, "all distinct elements should be stored");
        check(t.contains(999), "element 999 should be present");

        HashSet<String> u = new HashSet<>(4, Float.MIN_VALUE);
        u.add("x");
        check(u.size() == 1 && u.contains("x"), "tiny positive load factor should be accepted");
    }

    static void testInvalidInitialCapacity() {
        expectIllegalArgument(-1, 0.75f);
        expectIllegalArgument(Integer.MIN_VALUE, 0.75f);
    }

    static void testInvalidLoadFactor() {
        expectIllegalArgument(16, 0f);
        expectIllegalArgument(16, -1f);
        expectIllegalArgument(16, Float.NaN);
    }

    static void testLargeCapacityWithInvalidLoadFactor() {
        expectIllegalArgument((1 << 30) + 1, 0f);
        expectIllegalArgument(1 << 30, -0.5f);
    }

    static void testMaximumCapacityConstructionDoesNotThrow() {
        HashSet<String> s = new HashSet<>(Integer.MAX_VALUE, 0.75f);
        check(s.isEmpty(), "set created with large capacity should be empty");
    }

    static void expectIllegalArgument(int initialCapacity, float loadFactor) {
        try {
            new HashSet<Object>(initialCapacity, loadFactor);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for capacity="
                + initialCapacity + ", loadFactor=" + loadFactor);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
