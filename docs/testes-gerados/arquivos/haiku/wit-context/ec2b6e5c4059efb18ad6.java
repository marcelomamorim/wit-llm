/*
 * @test
 * @summary Witup-generated checks for ArrayDeque.push(E): LIFO ordering and NullPointerException on null element
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.push("a");
        deque.push("b");

        if (deque.size() != 2) {
            throw new AssertionError("Expected size 2 but was " + deque.size());
        }
        if (!"b".equals(deque.peekFirst())) {
            throw new AssertionError("Expected pushed element at head to be 'b' but was " + deque.peekFirst());
        }
        if (!"a".equals(deque.peekLast())) {
            throw new AssertionError("Expected 'a' at tail but was " + deque.peekLast());
        }

        boolean thrown = false;
        try {
            deque.push(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("push(null) must throw NullPointerException");
        }
        if (deque.size() != 2) {
            throw new AssertionError("Deque must be unchanged after rejected push(null), size was " + deque.size());
        }
        if (!"b".equals(deque.peekFirst())) {
            throw new AssertionError("Head changed after rejected push(null): " + deque.peekFirst());
        }
    }
}
