/*
 * @test
 * @summary Tests ArrayBlockingQueue.contains for null, empty, present, absent and wrapped-around elements
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        check(!q.contains(null), "null on empty queue must be false");
        check(!q.contains("a"), "element on empty queue must be false");

        q.add("a");
        q.add("b");
        q.add("c");
        check(q.contains("a"), "a must be found");
        check(q.contains("b"), "b must be found");
        check(q.contains("c"), "c must be found");
        check(!q.contains("z"), "absent element must be false");
        check(!q.contains(null), "null on non-empty queue must be false");

        q.poll();
        check(!q.contains("a"), "polled element must not be found");

        q.add("d");
        check(q.contains("d"), "element stored after wrap-around must be found");
        check(q.contains("b"), "b before wrap-around must be found");
        check(q.contains("c"), "c before wrap-around must be found");

        q.poll();
        q.poll();
        check(!q.contains("b"), "b polled must not be found");
        check(!q.contains("c"), "c polled must not be found");
        check(q.contains("d"), "d must still be found");

        ArrayBlockingQueue<Integer> ints = new ArrayBlockingQueue<>(2);
        ints.add(Integer.valueOf(5));
        check(ints.contains(Integer.valueOf(5)), "equal Integer must be found");
        check(!ints.contains(Integer.valueOf(6)), "non-equal Integer must not be found");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
