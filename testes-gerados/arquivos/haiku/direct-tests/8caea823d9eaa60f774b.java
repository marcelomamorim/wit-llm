/*
 * @test
 * @summary Verifies LinkedBlockingDeque.size() reflects observable deque contents
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testEmptySize();
        testSizeAfterAddsAndRemoves();
        testBoundedCapacitySize();
        testConstructorFromCollectionSize();
        testSizeAfterClear();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptySize() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<Integer>();
        check(d.size() == 0, "empty deque size should be 0");
        check(d.poll() == null, "poll on empty deque should return null");
        check(d.size() == 0, "size after failed poll should remain 0");
    }

    static void testSizeAfterAddsAndRemoves() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<Integer>();
        d.addFirst(1);
        d.addLast(2);
        check(d.offerFirst(0), "offerFirst should succeed on unbounded deque");
        check(d.size() == 3, "size should be 3 after three insertions");
        check(d.pollFirst() == 0, "pollFirst should return 0");
        check(d.size() == 2, "size should be 2 after pollFirst");
        check(d.pollLast() == 2, "pollLast should return 2");
        check(d.size() == 1, "size should be 1 after pollLast");
        check(d.removeFirst() == 1, "removeFirst should return 1");
        check(d.size() == 0, "size should be 0 after removing all elements");
    }

    static void testBoundedCapacitySize() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<Integer>(2);
        check(d.offer(1), "first offer should succeed");
        check(d.offerLast(2), "second offer should succeed");
        check(!d.offer(3), "offer beyond capacity should return false");
        check(d.size() == 2, "size should stay at capacity bound");
        check(d.remainingCapacity() == 0, "remainingCapacity should be 0 when full");
    }

    static void testConstructorFromCollectionSize() {
        LinkedBlockingDeque<Integer> d =
            new LinkedBlockingDeque<Integer>(Arrays.asList(10, 20, 30));
        check(d.size() == 3, "size should match source collection size");
        check(d.peekFirst() == 10, "first element should be 10");
        check(d.peekLast() == 30, "last element should be 30");
    }

    static void testSizeAfterClear() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<Integer>();
        d.add(1);
        d.add(2);
        d.add(3);
        check(d.size() == 3, "size should be 3 before clear");
        d.clear();
        check(d.size() == 0, "size should be 0 after clear");
        check(d.isEmpty(), "deque should be empty after clear");
    }
}
