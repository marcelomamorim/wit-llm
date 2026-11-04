/*
 * @test
 * @summary Test java.util.ArrayDeque add(E e) method
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {
    public static void main(String[] args) {
        testAddReturnsTrue();
        testAddIncreasesSize();
        testAddRejectsNull();
    }

    private static void testAddReturnsTrue() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        boolean result = deque.add("test");
        if (!result) {
            throw new AssertionError("add should return true");
        }
    }

    private static void testAddIncreasesSize() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        int initialSize = deque.size();
        deque.add("element");
        int newSize = deque.size();
        if (newSize != initialSize + 1) {
            throw new AssertionError("size should increase by 1 after add");
        }
        String first = deque.peekFirst();
        if (!"element".equals(first)) {
            throw new AssertionError("element added should be at the end");
        }
    }

    private static void testAddRejectsNull() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        try {
            deque.add(null);
            throw new AssertionError("add(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
