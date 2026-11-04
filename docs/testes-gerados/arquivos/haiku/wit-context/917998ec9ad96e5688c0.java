/*
 * @test
 * @summary Checks LinkedBlockingQueue.remove(Object) for null, absent, duplicate and present elements
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<String>();

        check(!q.remove(null), "remove(null) must return false");
        check(!q.remove("x"), "remove on empty queue must return false");
        check(q.isEmpty(), "queue must remain empty");

        q.add("a");
        q.add("b");
        q.add("a");

        check(!q.remove(null), "remove(null) must return false on non-empty queue");
        check(q.size() == 3, "remove(null) must not change size");

        check(!q.remove("c"), "remove of absent element must return false");
        check(q.size() == 3, "remove of absent element must not change size");

        check(q.remove("a"), "remove of present element must return true");
        check(q.size() == 2, "remove must decrease size by one");
        check("b".equals(q.peek()), "remove must keep FIFO order of remaining elements");

        check(q.remove("a"), "remove of duplicate element must return true");
        check(q.size() == 1, "second remove must decrease size by one");
        check("b".equals(q.peek()), "head must remain b");

        check(!q.remove("a"), "removed element must no longer be found");
        check(q.remove("b"), "remove of last element must return true");
        check(q.isEmpty(), "queue must be empty after removing all elements");
    }
}
