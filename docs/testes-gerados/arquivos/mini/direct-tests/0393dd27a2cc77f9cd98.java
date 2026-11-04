/*
 * @test
 * @summary Test LinkedBlockingDeque.drainTo(Collection) method
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.ArrayList;
import java.util.Collection;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testDrainToEmptyDeque();
        testDrainToNonEmptyDeque();
        testDrainToNullCollection();
    }

    static void testDrainToEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        Collection<String> target = new ArrayList<>();
        int drained = deque.drainTo(target);
        if (drained != 0) {
            throw new AssertionError("Expected 0 elements drained from empty deque, got " + drained);
        }
        if (!target.isEmpty()) {
            throw new AssertionError("Target collection should be empty after draining from empty deque");
        }
    }

    static void testDrainToNonEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("one");
        deque.add("two");
        deque.add("three");
        Collection<String> target = new ArrayList<>();
        int drained = deque.drainTo(target);
        if (drained != 3) {
            throw new AssertionError("Expected 3 elements drained, got " + drained);
        }
        if (deque.size() != 0) {
            throw new AssertionError("Deque should be empty after drainTo");
        }
        if (target.size() != 3 || !target.contains("one") || !target.contains("two") || !target.contains("three")) {
            throw new AssertionError("Target collection does not contain expected elements after drainTo");
        }
    }

    static void testDrainToNullCollection() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean threw = false;
        try {
            deque.drainTo(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException when passing null collection to drainTo");
        }
    }
}
