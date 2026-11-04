/*
 * @test
 * @summary Observable behavior of LinkedBlockingDeque.remainingCapacity()
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void checkEquals(int expected, int actual, String msg) {
        if (expected != actual) {
            throw new RuntimeException(msg + ": expected " + expected + " but was " + actual);
        }
    }

    public static void main(String[] args) {
        testUnboundedDefault();
        testBoundedCapacity();
        testDecreasesAndIncreasesWithOperations();
        testFullDequeReportsZero();
        testCollectionConstructor();
    }

    static void testUnboundedDefault() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        checkEquals(Integer.MAX_VALUE, d.remainingCapacity(), "default capacity");
        d.addLast("a");
        checkEquals(Integer.MAX_VALUE - 1, d.remainingCapacity(), "after one insert");
    }

    static void testBoundedCapacity() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>(5);
        checkEquals(5, d.remainingCapacity(), "empty bounded deque");
        d.addFirst(1);
        d.addFirst(2);
        checkEquals(3, d.remainingCapacity(), "after two inserts");
    }

    static void testDecreasesAndIncreasesWithOperations() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>(3);
        d.addLast(10);
        d.addLast(20);
        checkEquals(1, d.remainingCapacity(), "after two addLast");
        d.pollFirst();
        checkEquals(2, d.remainingCapacity(), "after pollFirst");
        d.removeLast();
        checkEquals(3, d.remainingCapacity(), "after removeLast");
    }

    static void testFullDequeReportsZero() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>(2);
        check(d.offerLast(1), "first offer should succeed");
        check(d.offerLast(2), "second offer should succeed");
        checkEquals(0, d.remainingCapacity(), "full deque");
        check(!d.offerLast(3), "offer to full deque should return false");
        checkEquals(0, d.remainingCapacity(), "still full after rejected offer");
    }

    static void testCollectionConstructor() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>(Arrays.asList(1, 2, 3));
        checkEquals(Integer.MAX_VALUE - 3, d.remainingCapacity(), "collection constructor");
    }
}
