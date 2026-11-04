/*
 * @test
 * @summary Checks LinkedTransferQueue spliterator tryAdvance observable behavior
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testEmptyQueueReturnsFalse();
        testFifoOrderAndExhaustion();
        testNullActionThrowsNpe();
        testAfterPollReturnsFalse();
        testCollectionConstructorElements();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyQueueReturnsFalse() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        Spliterator<String> sp = q.spliterator();
        List<String> seen = new ArrayList<>();
        boolean advanced = sp.tryAdvance(seen::add);
        check(!advanced, "tryAdvance on empty queue must return false");
        check(seen.isEmpty(), "action must not be called on empty queue");
    }

    static void testFifoOrderAndExhaustion() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        q.add("a");
        q.add("b");
        Spliterator<String> sp = q.spliterator();
        List<String> seen = new ArrayList<>();

        check(sp.tryAdvance(seen::add), "first tryAdvance must return true");
        check(seen.equals(Arrays.asList("a")), "first element must be a, got " + seen);

        check(sp.tryAdvance(seen::add), "second tryAdvance must return true");
        check(seen.equals(Arrays.asList("a", "b")), "second element must be b, got " + seen);

        check(!sp.tryAdvance(seen::add), "third tryAdvance must return false");
        check(seen.size() == 2, "action must not be called after exhaustion");
    }

    static void testNullActionThrowsNpe() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        q.add("x");
        Spliterator<String> sp = q.spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) must throw NullPointerException");
    }

    static void testAfterPollReturnsFalse() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        q.add("x");
        q.poll();
        Spliterator<String> sp = q.spliterator();
        List<String> seen = new ArrayList<>();
        check(!sp.tryAdvance(seen::add), "tryAdvance after poll of only element must return false");
        check(seen.isEmpty(), "action must not be called after element was polled");
    }

    static void testCollectionConstructorElements() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>(Arrays.asList("p", "q"));
        Spliterator<String> sp = q.spliterator();
        List<String> seen = new ArrayList<>();
        check(sp.tryAdvance(seen::add), "tryAdvance must return true for constructor elements");
        check(seen.equals(Arrays.asList("p")), "first constructor element must be p, got " + seen);
    }
}
