/*
 * @test
 * @summary Checks LinkedTransferQueue spliterator forEachRemaining: null action rejection, empty queue, FIFO traversal and traversal after partial consumption
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyQueueDoesNotInvokeAction();
        testTraversesAllElementsInFifoOrder();
        testRemainingElementsAfterTryAdvance();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        Spliterator<Integer> s = q.spliterator();
        boolean thrown = false;
        try {
            s.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEachRemaining(null) must throw NullPointerException");
    }

    static void testEmptyQueueDoesNotInvokeAction() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        List<Integer> out = new ArrayList<>();
        q.spliterator().forEachRemaining(out::add);
        check(out.isEmpty(), "empty queue must not produce elements");
    }

    static void testTraversesAllElementsInFifoOrder() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(10);
        q.add(20);
        q.add(30);
        List<Integer> out = new ArrayList<>();
        q.spliterator().forEachRemaining(out::add);
        check(out.size() == 3, "expected 3 elements but got " + out.size());
        check(out.get(0) == 10, "first element must be 10");
        check(out.get(1) == 20, "second element must be 20");
        check(out.get(2) == 30, "third element must be 30");
    }

    static void testRemainingElementsAfterTryAdvance() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        q.add(1);
        q.add(2);
        q.add(3);
        Spliterator<Integer> s = q.spliterator();
        List<Integer> first = new ArrayList<>();
        boolean advanced = s.tryAdvance(first::add);
        check(advanced, "tryAdvance must report an element");
        check(first.size() == 1 && first.get(0) == 1, "tryAdvance must consume head element 1");
        List<Integer> rest = new ArrayList<>();
        s.forEachRemaining(rest::add);
        check(rest.size() == 2, "expected 2 remaining elements but got " + rest.size());
        check(rest.get(0) == 2 && rest.get(1) == 3, "remaining elements must be 2 then 3");
    }
}
