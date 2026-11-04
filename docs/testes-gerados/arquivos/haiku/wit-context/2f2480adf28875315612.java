/*
 * @test
 * @summary WITUP-derived checks for Vector(int initialCapacity) constructor
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testNegativeInitialCapacityThrows();
        testZeroInitialCapacityCreatesEmptyUsableVector();
        testPositiveInitialCapacityCreatesEmptyVector();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativeInitialCapacityThrows() {
        int[] invalid = { -1, Integer.MIN_VALUE };
        for (int capacity : invalid) {
            boolean thrown = false;
            try {
                new Vector<String>(capacity);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            check(thrown, "Expected IllegalArgumentException for initialCapacity " + capacity);
        }
    }

    static void testZeroInitialCapacityCreatesEmptyUsableVector() {
        Vector<String> v = new Vector<String>(0);
        check(v.isEmpty(), "Vector(0) should be empty");
        check(v.size() == 0, "Vector(0) size should be 0");
        v.add("a");
        check(v.size() == 1, "Vector(0) should accept an element");
        check("a".equals(v.get(0)), "Vector(0) element should be retrievable");
    }

    static void testPositiveInitialCapacityCreatesEmptyVector() {
        Vector<String> v = new Vector<String>(10);
        check(v.isEmpty(), "Vector(10) should be empty");
        check(v.size() == 0, "Vector(10) size should be 0");
        for (int i = 0; i < 15; i++) {
            v.add("e" + i);
        }
        check(v.size() == 15, "Vector(10) should grow beyond initial capacity");
        check("e14".equals(v.get(14)), "Last element should be retrievable after growth");
    }
}
