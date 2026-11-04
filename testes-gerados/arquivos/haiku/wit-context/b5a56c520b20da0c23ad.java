/*
 * @test
 * @summary Witup-derived tests for ConcurrentLinkedDeque.remove(Object)
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeWitupTest {
    public static void main(String[] args) {
        ConcurrentLinkedDeque<String> empty = new ConcurrentLinkedDeque<>();
        if (empty.remove("a")) {
            throw new AssertionError("remove on empty deque returned true");
        }

        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        d.add("a");
        d.add("b");
        d.add("a");
        if (!d.remove("a")) {
            throw new AssertionError("remove of present element returned false");
        }
        if (d.size() != 2 || !d.getFirst().equals("b") || !d.getLast().equals("a")) {
            throw new AssertionError("remove did not delete first occurrence only");
        }
        if (!d.remove("b")) {
            throw new AssertionError("remove of 'b' returned false");
        }
        if (d.remove("zz")) {
            throw new AssertionError("remove of absent element returned true");
        }
        if (d.size() != 1 || !d.getFirst().equals("a")) {
            throw new AssertionError("unexpected deque state after removals");
        }

        try {
            d.remove(null);
            throw new AssertionError("remove(null) did not throw NullPointerException");
        } catch (NullPointerException expected) {
        }
        if (d.size() != 1 || !d.getFirst().equals("a")) {
            throw new AssertionError("deque modified by remove(null)");
        }
    }
}
