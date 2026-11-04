/*
 * @test
 * @summary Tests the public Vector(int initialCapacity, int capacityIncrement) constructor
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testDefaultStateAndAdd();
        testZeroCapacityAndIncrementGrows();
        testNegativeIncrementAccepted();
        testNegativeInitialCapacityThrows();
    }

    static void testDefaultStateAndAdd() {
        Vector<String> v = new Vector<>(10, 5);
        check(v.size() == 0, "new vector size should be 0");
        check(v.isEmpty(), "new vector should be empty");
        v.add("a");
        v.add("b");
        check(v.size() == 2, "size should be 2 after two adds");
        check("b".equals(v.get(1)), "element at index 1 should be b");
    }

    static void testZeroCapacityAndIncrementGrows() {
        Vector<String> z = new Vector<>(0, 0);
        check(z.isEmpty(), "zero-capacity vector should start empty");
        for (int i = 0; i < 100; i++) {
            z.add("x" + i);
        }
        check(z.size() == 100, "size should be 100");
        check("x0".equals(z.get(0)), "first element should be x0");
        check("x99".equals(z.get(99)), "last element should be x99");
    }

    static void testNegativeIncrementAccepted() {
        Vector<Integer> n = new Vector<>(2, -3);
        for (int i = 0; i < 5; i++) {
            n.add(i);
        }
        check(n.size() == 5, "size should be 5");
        check(n.get(4) == 4, "last element should be 4");
    }

    static void testNegativeInitialCapacityThrows() {
        boolean thrown = false;
        try {
            new Vector<String>(-1, 1);
        } catch (IllegalArgumentException e) {
            thrown = true;
            check("Illegal Capacity: -1".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
        check(thrown, "negative initialCapacity should throw IllegalArgumentException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
