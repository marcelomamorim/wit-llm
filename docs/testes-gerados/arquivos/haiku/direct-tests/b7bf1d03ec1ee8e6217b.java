/*
 * @test
 * @summary Verifies LinkedBlockingDeque.offer(E) inserts at the tail and reports capacity via its boolean result
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testOfferAddsAtTail();
        testOfferReturnsFalseWhenFull();
        testOfferMatchesOfferLast();
        testOfferAfterPollFreesCapacity();
        testOfferOnCollectionConstructedDeque();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testOfferAddsAtTail() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        check(d.offer("a"), "offer(a) should return true");
        check(d.offer("b"), "offer(b) should return true");
        check(d.size() == 2, "size should be 2 but was " + d.size());
        check("a".equals(d.peekFirst()), "first element should be a");
        check("b".equals(d.peekLast()), "last element should be b");
        check("a".equals(d.poll()), "poll should return a first (FIFO)");
        check("b".equals(d.poll()), "poll should return b second");
        check(d.isEmpty(), "deque should be empty");
    }

    static void testOfferReturnsFalseWhenFull() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>(2);
        check(d.offer("1"), "first offer should succeed");
        check(d.offer("2"), "second offer should succeed");
        check(!d.offer("3"), "offer on full deque should return false");
        check(d.size() == 2, "size should remain 2 but was " + d.size());
        check("2".equals(d.peekLast()), "rejected element must not be added at tail");
        check(d.remainingCapacity() == 0, "remaining capacity should be 0");
    }

    static void testOfferMatchesOfferLast() {
        LinkedBlockingDeque<Integer> viaOffer = new LinkedBlockingDeque<>(3);
        LinkedBlockingDeque<Integer> viaOfferLast = new LinkedBlockingDeque<>(3);
        for (int i = 0; i < 5; i++) {
            boolean r1 = viaOffer.offer(i);
            boolean r2 = viaOfferLast.offerLast(i);
            check(r1 == r2, "offer and offerLast results differ at " + i);
        }
        check(viaOffer.size() == viaOfferLast.size(), "sizes differ");
        check(viaOffer.peekLast().equals(viaOfferLast.peekLast()), "tail elements differ");
    }

    static void testOfferAfterPollFreesCapacity() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>(1);
        check(d.offer("x"), "offer into empty bounded deque should succeed");
        check(!d.offer("y"), "offer into full bounded deque should fail");
        check("x".equals(d.poll()), "poll should return x");
        check(d.offer("y"), "offer should succeed after poll freed capacity");
        check("y".equals(d.peekLast()), "y should be at tail");
    }

    static void testOfferOnCollectionConstructedDeque() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>(Arrays.asList("p", "q"));
        check(d.offer("r"), "offer on unbounded collection-constructed deque should return true");
        check(d.size() == 3, "size should be 3 but was " + d.size());
        check("r".equals(d.peekLast()), "r should be appended at tail");
        check("p".equals(d.peekFirst()), "p should remain at head");
    }
}
