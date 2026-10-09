/* @test    @summary Test addLast method of ArrayDeque for correct behavior and null handling    @run main ArrayDequeWitupTest_P4c3dc95c8757 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest_P4c3dc95c8757 {

    public static void main(String[] args) {
        testAddLastWithElement();
        testAddLastWithNull();
    }

    static void testAddLastWithElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.addLast("element");
        if (deque.isEmpty()) {
            throw new AssertionError("Deque should not be empty after addLast") ;
        }
        String first = deque.peekFirst();
        if (!"element".equals(first)) {
            throw new AssertionError("First element should be 'element'");
        }
    }

    static void testAddLastWithNull() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        try {
            deque.addLast(null);
            throw new AssertionError("Adding null should throw NullPointerException") ;
        } catch (NullPointerException expected) {
            // expected exception
        }
    }
}
