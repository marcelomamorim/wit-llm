/*
 * @test
 * @summary Test LinkedBlockingDeque.drainTo(Collection<? super E>, int) for basic correctness and exception handling
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.Collection;
import java.util.ArrayList;
import java.util.IllegalArgumentException;
import java.util.Objects;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        testNullCollectionThrowsNPE();
        testDrainToSelfThrowsIAE();
        testDrainToZeroOrNegativeReturnsZero();
        testDrainToDrainsUpToMaxElements();
    }

    private static void testNullCollectionThrowsNPE() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        boolean npeThrown = false;
        try {
            deque.drainTo(null, 1);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null collection");
        }
    }

    private static void testDrainToSelfThrowsIAE() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        boolean iaeThrown = false;
        try {
            deque.drainTo(deque, 1);
        } catch (IllegalArgumentException e) {
            iaeThrown = true;
        }
        if (!iaeThrown) {
            throw new AssertionError("Expected IllegalArgumentException when passing self as collection");
        }
    }

    private static void testDrainToZeroOrNegativeReturnsZero() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        int drained = deque.drainTo(new ArrayList<Integer>(), 0);
        if (drained != 0) {
            throw new AssertionError("Expected 0 elements drained when maxElements is 0");
        }
        drained = deque.drainTo(new ArrayList<Integer>(), -1);
        if (drained != 0) {
            throw new AssertionError("Expected 0 elements drained when maxElements is negative");
        }
    }

    private static void testDrainToDrainsUpToMaxElements() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        for (int i = 1; i <= 5; i++) {
            deque.add(i);
        }
        ArrayList<Integer> target = new ArrayList<>();
        int drained = deque.drainTo(target, 3);
        if (drained != 3) {
            throw new AssertionError("Expected to drain 3 elements, but drained " + drained);
        }
        if (target.size() != 3) {
            throw new AssertionError("Target collection size expected 3 but was " + target.size());
        }
        // The drained elements should be the first 3 added (1,2,3) in order
        for (int i = 0; i < 3; i++) {
            if (!target.get(i).equals(i + 1)) {
                throw new AssertionError("Expected element " + (i + 1) + " but got " + target.get(i));
            }
        }
        // The deque should now have 2 elements left (4,5)
        drained = deque.drainTo(new ArrayList<Integer>(), 10);
        if (drained != 2) {
            throw new AssertionError("Expected to drain remaining 2 elements, but drained " + drained);
        }
    }
}
