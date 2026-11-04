/*
 * @test
 * @summary Behavioural tests for LinkedBlockingQueue.iterator() derived from current checkout
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueueIterator();
        testFifoOrderThroughIterator();
        testNextAfterExhaustionThrows();
        testBoundedQueueIterator();
        testIndependentIterators();
    }

    static void testEmptyQueueIterator() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        Iterator<String> it = q.iterator();
        check(it != null, "iterator() must not return null");
        check(!it.hasNext(), "empty queue iterator must have no next element");
        expectNoSuchElement(it);
    }

    static void testFifoOrderThroughIterator() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.add("a");
        q.add("b");
        q.add("c");
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = q.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
        }
        check("abc".equals(sb.toString()), "iterator must traverse in FIFO order, got " + sb);
    }

    static void testNextAfterExhaustionThrows() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(42);
        Iterator<Integer> it = q.iterator();
        check(it.hasNext(), "iterator over one element must have next");
        check(it.next() == 42, "first element must be 42");
        check(!it.hasNext(), "iterator must be exhausted after one element");
        expectNoSuchElement(it);
    }

    static void testBoundedQueueIterator() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(2);
        check(q.offer(1), "offer(1) on capacity 2 must succeed");
        check(q.offer(2), "offer(2) on capacity 2 must succeed");
        check(!q.offer(3), "offer(3) on full queue must return false");
        int count = 0;
        int expected = 1;
        Iterator<Integer> it = q.iterator();
        while (it.hasNext()) {
            int v = it.next();
            check(v == expected, "expected " + expected + " but got " + v);
            expected++;
            count++;
        }
        check(count == 2, "bounded queue iterator must yield 2 elements, got " + count);
    }

    static void testIndependentIterators() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.add(1);
        q.add(2);
        Iterator<Integer> it1 = q.iterator();
        Iterator<Integer> it2 = q.iterator();
        check(it1 != it2, "each iterator() call must return a distinct iterator");
        check(it1.next() == 1, "first iterator must start at 1");
        check(it2.next() == 1, "second iterator must start at 1 independently");
        check(it1.next() == 2, "first iterator must continue to 2");
        check(it2.next() == 2, "second iterator must continue to 2");
        check(!it1.hasNext() && !it2.hasNext(), "both iterators must be exhausted");
    }

    static void expectNoSuchElement(Iterator<?> it) {
        try {
            it.next();
        } catch (NoSuchElementException expected) {
            return;
        }
        throw new RuntimeException("next() on exhausted iterator must throw NoSuchElementException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
