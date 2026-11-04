/*
 * @test
 * @summary Observable behavior of LinkedBlockingQueue.toArray()
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueue();
        testElementOrder();
        testReturnedArrayIsCopy();
        testBoundedQueue();
        testCollectionConstructor();
    }

    static void testEmptyQueue() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<Integer>();
        Object[] a = q.toArray();
        check(a != null, "toArray returned null for empty queue");
        check(a.length == 0, "expected empty array, got length " + a.length);
        check(a.getClass() == Object[].class, "expected Object[] runtime type");
    }

    static void testElementOrder() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<Integer>(10);
        for (int i = 1; i <= 5; i++) {
            check(q.offer(i), "offer failed for " + i);
        }
        Object[] a = q.toArray();
        check(Arrays.equals(a, new Object[] {1, 2, 3, 4, 5}),
              "unexpected order: " + Arrays.toString(a));
    }

    static void testReturnedArrayIsCopy() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<Integer>();
        q.add(1);
        q.add(2);
        Object[] a = q.toArray();
        q.poll();
        q.add(9);
        check(a.length == 2, "snapshot length changed: " + a.length);
        check(a[0].equals(1) && a[1].equals(2),
              "snapshot modified by queue changes: " + Arrays.toString(a));
        Object[] b = q.toArray();
        check(Arrays.equals(b, new Object[] {2, 9}),
              "unexpected contents after changes: " + Arrays.toString(b));
    }

    static void testBoundedQueue() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<String>(2);
        check(q.offer("a"), "first offer failed");
        check(q.offer("b"), "second offer failed");
        check(!q.offer("c"), "offer beyond capacity should return false");
        Object[] a = q.toArray();
        check(Arrays.equals(a, new Object[] {"a", "b"}),
              "unexpected contents: " + Arrays.toString(a));
    }

    static void testCollectionConstructor() {
        LinkedBlockingQueue<String> q =
            new LinkedBlockingQueue<String>(Arrays.asList("x", "y", "z"));
        Object[] a = q.toArray();
        check(Arrays.equals(a, new Object[] {"x", "y", "z"}),
              "unexpected contents: " + Arrays.toString(a));
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
