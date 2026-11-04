/*
 * @test
 * @summary Tests for java.util.concurrent.LinkedBlockingDeque
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.Collection;
import java.util.ArrayList;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testDrainToNullCollection();
        testDrainToZeroMax();
        testDrainToNormal();
    }

    public static void testDrainToNullCollection() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.drainTo(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when draining to null collection");
        }
    }

    public static void testDrainToZeroMax() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        Collection<Integer> c = new ArrayList<>();
        int result = deque.drainTo(c, 0);
        if (result != 0) {
            throw new AssertionError("drainTo with max=0 should return 0");
        }
        if (c.size() != 0) {
            throw new AssertionError("Collection should remain empty when draining with max=0");
        }
    }

    public static void testDrainToNormal() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(10);
        deque.add(20);
        deque.add(30);
        Collection<Integer> c = new ArrayList<>();
        int drained = deque.drainTo(c, 2);
        if (drained != 2) {
            throw new AssertionError("drainTo should drain the specified maxElements");
        }
        if (c.size() != 2) {
            throw new AssertionError("Collection size should match drained elements");
        }
        if (deque.size() != 1) {
            throw new AssertionError("Deque should have remaining elements after drain");
        }
        if (!(c.contains(10) && c.contains(20))) {
            throw new AssertionError("Collection should contain drained elements");
        }
    }
}
