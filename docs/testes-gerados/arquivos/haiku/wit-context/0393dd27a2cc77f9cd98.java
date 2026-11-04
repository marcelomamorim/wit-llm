/*
 * @test
 * @summary Witup-derived tests for LinkedBlockingDeque.drainTo(Collection)
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testNullCollectionThrowsNPE();
        testEmptyDequeReturnsZero();
        testDrainsAllInOrder();
        testAppendsToExistingElements();
        testBoundedDequeDrainsAll();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullCollectionThrowsNPE() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        d.add(1);
        try {
            d.drainTo(null);
        } catch (NullPointerException e) {
            check(d.size() == 1, "deque must be unchanged after NPE");
            return;
        }
        throw new RuntimeException("expected NullPointerException for null collection");
    }

    static void testEmptyDequeReturnsZero() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        List<Integer> out = new ArrayList<>();
        int n = d.drainTo(out);
        check(n == 0, "expected 0 drained from empty deque, got " + n);
        check(out.isEmpty(), "target must remain empty");
    }

    static void testDrainsAllInOrder() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        d.add(1);
        d.add(2);
        d.add(3);
        List<Integer> out = new ArrayList<>();
        int n = d.drainTo(out);
        check(n == 3, "expected 3 drained, got " + n);
        check(out.equals(Arrays.asList(1, 2, 3)), "unexpected order: " + out);
        check(d.isEmpty(), "deque must be empty after drainTo");
    }

    static void testAppendsToExistingElements() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        d.add(7);
        d.add(8);
        List<Integer> out = new ArrayList<>(Arrays.asList(0));
        int n = d.drainTo(out);
        check(n == 2, "expected 2 drained, got " + n);
        check(out.equals(Arrays.asList(0, 7, 8)), "unexpected content: " + out);
    }

    static void testBoundedDequeDrainsAll() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>(3);
        d.add(4);
        d.add(5);
        d.add(6);
        List<Integer> out = new ArrayList<>();
        int n = d.drainTo(out);
        check(n == 3, "expected 3 drained from bounded deque, got " + n);
        check(out.equals(Arrays.asList(4, 5, 6)), "unexpected content: " + out);
        check(d.remainingCapacity() == 3, "capacity must be available after drain");
    }
}
