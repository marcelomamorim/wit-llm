/*
 * @test
 * @summary Witup-generated behavioral checks for LinkedBlockingDeque.drainTo
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static class FailingOnSecondAdd extends ArrayList<Integer> {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean add(Integer e) {
            if (size() >= 1) {
                throw new IllegalStateException("boom");
            }
            return super.add(e);
        }
    }

    static void testNullCollectionThrowsNPE() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        try {
            deque.drainTo(null, 1);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            check(deque.size() == 1, "deque must be unchanged after NPE");
        }
    }

    static void testSelfDrainThrowsIAE() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        try {
            deque.drainTo(deque, 2);
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            check(deque.size() == 2, "deque must be unchanged after IAE");
        }
    }

    static void testNonPositiveMaxReturnsZero() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        ArrayList<Integer> sink = new ArrayList<>();
        check(deque.drainTo(sink, 0) == 0, "maxElements=0 must return 0");
        check(deque.drainTo(sink, -5) == 0, "negative maxElements must return 0");
        check(sink.isEmpty(), "sink must stay empty");
        check(deque.size() == 2, "deque must keep its elements");
    }

    static void testPartialDrainPreservesOrder() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        for (int i = 1; i <= 5; i++) {
            deque.add(i);
        }
        ArrayList<Integer> sink = new ArrayList<>();
        int n = deque.drainTo(sink, 3);
        check(n == 3, "expected 3 drained, got " + n);
        check(sink.equals(java.util.Arrays.asList(1, 2, 3)), "wrong drain order: " + sink);
        check(deque.size() == 2, "remaining size should be 2");
        check(deque.poll() == 4, "next element should be 4");
        check(deque.poll() == 5, "next element should be 5");
    }

    static void testDrainMoreThanAvailable() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(7);
        deque.add(8);
        ArrayList<Integer> sink = new ArrayList<>();
        int n = deque.drainTo(sink, Integer.MAX_VALUE);
        check(n == 2, "expected 2 drained, got " + n);
        check(sink.equals(java.util.Arrays.asList(7, 8)), "wrong contents: " + sink);
        check(deque.isEmpty(), "deque must be empty");
    }

    static void testDrainEmptyDequeReturnsZero() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        ArrayList<Integer> sink = new ArrayList<>();
        check(deque.drainTo(sink, 10) == 0, "empty deque must drain 0");
        check(sink.isEmpty(), "sink must stay empty");
    }

    static void testFailingCollectionKeepsUndrainedElements() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        FailingOnSecondAdd sink = new FailingOnSecondAdd();
        try {
            deque.drainTo(sink, 3);
            throw new RuntimeException("expected IllegalStateException from add");
        } catch (IllegalStateException expected) {
            check(sink.size() == 1 && sink.get(0) == 1, "only first element should be transferred");
            check(deque.size() == 2, "failed element must remain in deque, size=" + deque.size());
            check(deque.poll() == 2, "head after failure should be 2");
            check(deque.poll() == 3, "next after failure should be 3");
        }
    }

    public static void main(String[] args) {
        testNullCollectionThrowsNPE();
        testSelfDrainThrowsIAE();
        testNonPositiveMaxReturnsZero();
        testPartialDrainPreservesOrder();
        testDrainMoreThanAvailable();
        testDrainEmptyDequeReturnsZero();
        testFailingCollectionKeepsUndrainedElements();
    }
}
