/*
 * @test
 * @summary Behavioral tests for LinkedBlockingDeque.poll() using only public API
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testPollOnEmptyDequeReturnsNull();
        testPollReturnsHeadInFifoOrder();
        testPollOnBoundedDequeAfterOffer();
        testPollOnCollectionConstructedDeque();
        testPollAfterDrainReturnsNull();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPollOnEmptyDequeReturnsNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String value = deque.poll();
        check(value == null, "poll() on empty deque must return null, got " + value);
        check(deque.isEmpty(), "deque must remain empty after poll()");
    }

    static void testPollReturnsHeadInFifoOrder() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        check(deque.poll() == 1, "first poll() must return 1");
        check(deque.poll() == 2, "second poll() must return 2");
        check(deque.size() == 1, "size must be 1 after two polls");
        check(deque.poll() == 3, "third poll() must return 3");
        check(deque.isEmpty(), "deque must be empty after removing all elements");
    }

    static void testPollOnBoundedDequeAfterOffer() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(1);
        check(deque.offerLast("a"), "offerLast must succeed on empty bounded deque");
        check(!deque.offerLast("b"), "offerLast must fail when bounded deque is full");
        check("a".equals(deque.poll()), "poll() must return the only element");
        check(deque.poll() == null, "poll() after removing the only element must return null");
        check(deque.offerLast("c"), "offerLast must succeed again after poll freed capacity");
        check("c".equals(deque.poll()), "poll() must return the re-inserted element");
    }

    static void testPollOnCollectionConstructedDeque() {
        List<String> source = Arrays.asList("x", "y");
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(source);
        check("x".equals(deque.poll()), "poll() must return first element of source collection");
        check("y".equals(deque.poll()), "poll() must return second element of source collection");
        check(deque.poll() == null, "poll() on exhausted deque must return null");
    }

    static void testPollAfterDrainReturnsNull() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.addFirst(10);
        check(deque.poll() == 10, "poll() must return the added element");
        for (int i = 0; i < 3; i++) {
            check(deque.poll() == null, "repeated poll() on empty deque must return null");
        }
        check(deque.isEmpty(), "deque must remain empty after repeated polls");
    }
}
