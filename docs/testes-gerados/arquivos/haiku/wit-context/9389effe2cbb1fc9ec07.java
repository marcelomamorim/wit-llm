/*
 * @test
 * @summary Witup-style tests for LinkedBlockingQueue(Collection) constructor
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testCopiesElementsInOrder();
        testEmptyCollection();
        testNullElementRejected();
        testSourceModificationDoesNotAffectQueue();
        testResultingQueueIsUnbounded();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testCopiesElementsInOrder() {
        List<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(source);
        check(q.size() == 3, "size should be 3 but was " + q.size());
        check(q.poll() == 1, "first element should be 1");
        check(q.poll() == 2, "second element should be 2");
        check(q.poll() == 3, "third element should be 3");
        check(q.poll() == null, "queue should be empty after draining");
    }

    static void testEmptyCollection() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>(new ArrayList<String>());
        check(q.isEmpty(), "queue built from empty collection should be empty");
        check(q.size() == 0, "size should be 0");
        check(q.peek() == null, "peek should return null on empty queue");
    }

    static void testNullElementRejected() {
        List<String> source = new ArrayList<>();
        source.add("a");
        source.add(null);
        boolean thrown = false;
        try {
            new LinkedBlockingQueue<String>(source);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "collection containing null should raise NullPointerException");
    }

    static void testSourceModificationDoesNotAffectQueue() {
        List<String> source = new ArrayList<>();
        source.add("x");
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>(source);
        source.add("y");
        check(q.size() == 1, "queue should hold a copy, size should remain 1 but was " + q.size());
        check("x".equals(q.peek()), "head should remain x");
    }

    static void testResultingQueueIsUnbounded() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(Arrays.asList(1, 2));
        check(q.remainingCapacity() == Integer.MAX_VALUE,
              "remaining capacity should be Integer.MAX_VALUE");
        check(q.offer(3), "offer should succeed on unbounded queue");
        check(q.size() == 3, "size should be 3 after offer");
    }
}
