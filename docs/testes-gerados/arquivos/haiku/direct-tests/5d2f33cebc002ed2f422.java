/*
 * @test
 * @summary Verify LinkedBlockingDeque.poll() returns the head element, removes it, and returns null when empty
 * @run main LinkedBlockingDequeWitupTest
 */
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testEmptyReturnsNull();
        testFifoOrder();
        testHeadAfterOfferFirst();
        testBoundedCapacity();
        testFromCollection();
        testPollMatchesPeekFirst();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyReturnsNull() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        check(d.poll() == null, "poll() on empty deque must return null");
        check(d.size() == 0, "size must remain 0 after poll() on empty deque");
    }

    static void testFifoOrder() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        check(d.offerLast("a"), "offerLast a");
        check(d.offerLast("b"), "offerLast b");
        check("a".equals(d.poll()), "first poll must return a");
        check("b".equals(d.poll()), "second poll must return b");
        check(d.poll() == null, "third poll must return null");
        check(d.isEmpty(), "deque must be empty");
    }

    static void testHeadAfterOfferFirst() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        check(d.offerFirst("x"), "offerFirst x");
        check(d.offerLast("y"), "offerLast y");
        check("x".equals(d.poll()), "poll must return head x");
        check("y".equals(d.poll()), "poll must then return y");
    }

    static void testBoundedCapacity() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>(1);
        check(d.offerLast("a"), "offerLast a into capacity 1");
        check("a".equals(d.poll()), "poll must return a");
        check(d.poll() == null, "poll on empty bounded deque must return null");
        check(d.offerLast("b"), "capacity must be free again after poll");
        check("b".equals(d.poll()), "poll must return b");
    }

    static void testFromCollection() {
        Collection<Integer> src = Arrays.asList(1, 2, 3);
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>(src);
        Integer first = d.poll();
        check(first != null && first.intValue() == 1, "poll must return first element 1");
        check(d.size() == 2, "size must be 2 after one poll");
    }

    static void testPollMatchesPeekFirst() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.offerLast("p");
        d.offerLast("q");
        String peeked = d.peekFirst();
        String polled = d.poll();
        check(peeked != null && peeked.equals(polled), "poll must return the same element as peekFirst");
        check(d.size() == 1, "size must be 1 after poll");
        check("q".equals(d.peekFirst()), "remaining head must be q");
    }
}
