/*
 * @test
 * @summary Verify observable hasNext behavior of the ArrayBlockingQueue iterator
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueueIteratorHasNextFalse();
        testHasNextTrueWhileElementsRemain();
        testHasNextIsIdempotent();
        testHasNextFalseAfterExhaustion();
        testIteratorFromCollectionConstructor();
    }

    static void testEmptyQueueIteratorHasNextFalse() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        Iterator<Integer> it = q.iterator();
        check(!it.hasNext(), "empty queue iterator should have no next");
        check(!it.hasNext(), "repeated hasNext on empty iterator should stay false");
    }

    static void testHasNextTrueWhileElementsRemain() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.add(1);
        q.add(2);
        Iterator<Integer> it = q.iterator();
        check(it.hasNext(), "hasNext should be true with elements present");
        check(it.next() == 1, "first element should be 1");
        check(it.hasNext(), "hasNext should be true before second element");
        check(it.next() == 2, "second element should be 2");
    }

    static void testHasNextIsIdempotent() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        q.add(10);
        Iterator<Integer> it = q.iterator();
        check(it.hasNext(), "first hasNext should be true");
        check(it.hasNext(), "second hasNext should be true");
        check(it.hasNext(), "third hasNext should be true");
        check(it.next() == 10, "next should still return 10");
    }

    static void testHasNextFalseAfterExhaustion() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(2);
        q.add(5);
        Iterator<Integer> it = q.iterator();
        check(it.hasNext(), "hasNext should be true before consuming");
        check(it.next() == 5, "next should return 5");
        check(!it.hasNext(), "hasNext should be false after exhaustion");
        check(!it.hasNext(), "repeated hasNext after exhaustion should be false");
    }

    static void testIteratorFromCollectionConstructor() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(5, false, Arrays.asList(7, 8));
        Iterator<Integer> it = q.iterator();
        check(it.hasNext(), "iterator over pre-filled queue should have next");
        check(it.next() == 7, "first pre-filled element should be 7");
        check(it.hasNext(), "second pre-filled element should be reachable");
        check(it.next() == 8, "second pre-filled element should be 8");
        check(!it.hasNext(), "no elements should remain");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
