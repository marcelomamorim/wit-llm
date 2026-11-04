/*
 * @test
 * @summary Witup-derived tests for ArrayBlockingQueue.forEach null action and ordering
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testEmptyQueueDoesNotInvokeAction();
        testFifoOrderAfterWraparound();
        testFullQueueOrderAndUnchangedSize();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNpe() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(2);
        q.add("a");
        boolean thrown = false;
        try {
            q.forEach(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "forEach(null) must throw NullPointerException");
    }

    static void testEmptyQueueDoesNotInvokeAction() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(2);
        final List<String> seen = new ArrayList<>();
        q.forEach(seen::add);
        check(seen.isEmpty(), "forEach on empty queue must not invoke action");
    }

    static void testFifoOrderAfterWraparound() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.add(1);
        q.add(2);
        q.add(3);
        check(q.poll().equals(1), "poll must return head 1");
        q.add(4);
        final List<Integer> seen = new ArrayList<>();
        q.forEach(seen::add);
        check(Arrays.asList(2, 3, 4).equals(seen),
              "forEach must visit elements in FIFO order after wraparound, got " + seen);
    }

    static void testFullQueueOrderAndUnchangedSize() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(3);
        q.add("x");
        q.add("y");
        q.add("z");
        final List<String> seen = new ArrayList<>();
        q.forEach(seen::add);
        check(Arrays.asList("x", "y", "z").equals(seen),
              "forEach must visit elements in insertion order, got " + seen);
        check(q.size() == 3, "forEach must not change queue size");
    }
}
