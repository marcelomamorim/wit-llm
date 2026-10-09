/* @test    @summary Test addLast method of ArrayDeque for normal and null input behavior    @run main ArrayDequeWitupTest_P05035dc1d048 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest_P05035dc1d048 {

    public static void main(String[] args) {
        testAddLastWithElement();
        testAddLastWithNull();
    }

    static void testAddLastWithElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        String element = "test";
        deque.addLast(element);
        if (deque.isEmpty()) {
            throw new AssertionError("FAIL: deque should not be empty after addLast")
        }
        String first = deque.peekFirst();
        if (!element.equals(first)) {
            throw new AssertionError("FAIL: first element should be 'test'");
        }
    }

    static void testAddLastWithNull() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.addLast(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("FAIL: addLast(null) should throw NullPointerException")
        }
    }
}
