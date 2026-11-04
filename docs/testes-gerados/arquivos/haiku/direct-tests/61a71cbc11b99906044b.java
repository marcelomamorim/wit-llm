/*
 * @test
 * @summary Tests ArrayDeque.offer(E) appends at the tail, returns true, grows, and rejects null
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.Iterator;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testReturnsTrueAndAppendsAtTail();
        testInsertionOrderPreserved();
        testGrowthWithManyElements();
        testNullRejected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReturnsTrueAndAppendsAtTail() {
        ArrayDeque<String> d = new ArrayDeque<>();
        check(d.offer("a"), "offer should return true");
        check(d.offer("b"), "offer should return true for second element");
        check(d.size() == 2, "size should be 2 but was " + d.size());
        check("b".equals(d.peekLast()), "last element should be b");
        check("a".equals(d.peekFirst()), "first element should be a");
    }

    static void testInsertionOrderPreserved() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        for (int i = 1; i <= 5; i++) {
            check(d.offer(i), "offer should return true for " + i);
        }
        for (int i = 1; i <= 5; i++) {
            Integer v = d.pollFirst();
            check(v != null && v == i, "expected " + i + " but got " + v);
        }
        check(d.isEmpty(), "deque should be empty after polling all elements");
    }

    static void testGrowthWithManyElements() {
        ArrayDeque<Integer> d = new ArrayDeque<>(1);
        int n = 1000;
        for (int i = 0; i < n; i++) {
            check(d.offer(i), "offer should return true at index " + i);
        }
        check(d.size() == n, "size should be " + n + " but was " + d.size());
        check(Integer.valueOf(0).equals(d.peekFirst()), "first element should be 0");
        check(Integer.valueOf(n - 1).equals(d.peekLast()), "last element should be " + (n - 1));
        Iterator<Integer> it = d.iterator();
        int expected = 0;
        while (it.hasNext()) {
            Integer v = it.next();
            check(v == expected, "expected " + expected + " but got " + v);
            expected++;
        }
        check(expected == n, "iterated count should be " + n);
    }

    static void testNullRejected() {
        ArrayDeque<String> d = new ArrayDeque<>();
        boolean threw = false;
        try {
            d.offer(null);
        } catch (NullPointerException expected) {
            threw = true;
        }
        check(threw, "offer(null) should throw NullPointerException");
        check(d.size() == 0, "deque should remain empty after rejected null");
    }
}
