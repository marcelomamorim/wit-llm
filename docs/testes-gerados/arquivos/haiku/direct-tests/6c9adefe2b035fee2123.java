/*
 * @test
 * @summary Behavioral tests for ArrayDeque.offerFirst: return value, head insertion order, null rejection, growth and small initial capacity
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testReturnsTrue();
        testHeadOrdering();
        testNullRejected();
        testGrowthPreservesOrder();
        testSmallInitialCapacity();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReturnsTrue() {
        ArrayDeque<String> d = new ArrayDeque<>();
        check(d.offerFirst("a") == true, "offerFirst must return true for first element");
        check(d.offerFirst("b") == true, "offerFirst must return true for subsequent element");
        check(d.size() == 2, "size must be 2 after two offerFirst calls");
    }

    static void testHeadOrdering() {
        ArrayDeque<String> d = new ArrayDeque<>();
        d.offerFirst("a");
        d.offerFirst("b");
        check("b".equals(d.peekFirst()), "last offered element must be at head");
        check("a".equals(d.peekLast()), "first offered element must be at tail");
    }

    static void testNullRejected() {
        ArrayDeque<String> d = new ArrayDeque<>();
        boolean thrown = false;
        try {
            d.offerFirst(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "offerFirst(null) must throw NullPointerException");
        check(d.size() == 0, "deque must remain empty after rejected null");
    }

    static void testGrowthPreservesOrder() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        int n = 1000;
        for (int i = 0; i < n; i++) {
            check(d.offerFirst(Integer.valueOf(i)), "offerFirst must return true at index " + i);
        }
        check(d.size() == n, "size must equal number of offered elements");
        for (int i = n - 1; i >= 0; i--) {
            Integer v = d.pollFirst();
            check(v != null && v.intValue() == i, "expected " + i + " but got " + v);
        }
        check(d.isEmpty(), "deque must be empty after draining");
    }

    static void testSmallInitialCapacity() {
        ArrayDeque<String> d = new ArrayDeque<>(1);
        check(d.offerFirst("a"), "offerFirst must return true");
        check(d.offerFirst("b"), "offerFirst must return true");
        check(d.offerFirst("c"), "offerFirst must return true");
        check("c".equals(d.peekFirst()), "head must be last offered element");
        check("a".equals(d.peekLast()), "tail must be first offered element");
        check(d.size() == 3, "size must be 3");
    }
}
