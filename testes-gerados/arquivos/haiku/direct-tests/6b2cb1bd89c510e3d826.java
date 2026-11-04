/*
 * @test
 * @summary Behavioral checks for LinkedBlockingQueue spliterator tryAdvance
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Spliterator;

public class LinkedBlockingQueueWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullActionThrowsNpe() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(Arrays.asList(1, 2));
        Spliterator<Integer> s = q.spliterator();
        boolean thrown = false;
        try {
            s.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action must throw NullPointerException");
    }

    static void testEmptyQueueReturnsFalseWithoutAccept() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        Spliterator<Integer> s = q.spliterator();
        List<Integer> seen = new ArrayList<>();
        boolean advanced = s.tryAdvance(seen::add);
        check(!advanced, "empty queue must return false");
        check(seen.isEmpty(), "action must not be invoked on empty queue");
    }

    static void testIteratesElementsInFifoOrder() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(Arrays.asList(10, 20, 30));
        Spliterator<Integer> s = q.spliterator();
        List<Integer> seen = new ArrayList<>();
        check(s.tryAdvance(seen::add), "first tryAdvance must return true");
        check(s.tryAdvance(seen::add), "second tryAdvance must return true");
        check(s.tryAdvance(seen::add), "third tryAdvance must return true");
        check(!s.tryAdvance(seen::add), "fourth tryAdvance must return false");
        check(seen.equals(Arrays.asList(10, 20, 30)), "unexpected order: " + seen);
    }

    static void testExhaustedStaysExhausted() {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(Arrays.asList(1));
        Spliterator<Integer> s = q.spliterator();
        List<Integer> seen = new ArrayList<>();
        check(s.tryAdvance(seen::add), "element must be delivered");
        check(!s.tryAdvance(seen::add), "spliterator must be exhausted");
        q.add(2);
        check(!s.tryAdvance(seen::add), "exhausted spliterator must keep returning false");
        check(seen.equals(Arrays.asList(1)), "unexpected elements: " + seen);
    }

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testEmptyQueueReturnsFalseWithoutAccept();
        testIteratesElementsInFifoOrder();
        testExhaustedStaysExhausted();
    }
}
