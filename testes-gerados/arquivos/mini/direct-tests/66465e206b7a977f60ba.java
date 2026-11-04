/*
 * @test
 * @summary Test LinkedBlockingDeque.drainTo(Collection<? super E>, int) method
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IllegalArgumentException;
import java.util.Objects;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        testDrainToNullCollection();
        testDrainToSelfCollection();
        testDrainToZeroOrNegativeMaxElements();
        testDrainToPartialDrain();
        testDrainToFullDrain();
    }

    static void testDrainToNullCollection() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.drainTo(null, 1);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
        }
    }

    static void testDrainToSelfCollection() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.drainTo(deque, 1);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void testDrainToZeroOrNegativeMaxElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        int drained = deque.drainTo(new ArrayList<String>(), 0);
        if (drained != 0) {
            throw new RuntimeException("Expected 0 elements drained when maxElements=0, got " + drained);
        }
        drained = deque.drainTo(new ArrayList<String>(), -1);
        if (drained != 0) {
            throw new RuntimeException("Expected 0 elements drained when maxElements<0, got " + drained);
        }
    }

    static void testDrainToPartialDrain() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("one");
        deque.add("two");
        deque.add("three");
        ArrayList<String> target = new ArrayList<>();
        int drained = deque.drainTo(target, 2);
        if (drained != 2) {
            throw new RuntimeException("Expected 2 elements drained, got " + drained);
        }
        if (target.size() != 2 || !target.get(0).equals("one") || !target.get(1).equals("two")) {
            throw new RuntimeException("Drained elements incorrect: " + target);
        }
        if (deque.size() != 1 || !deque.peekFirst().equals("three")) {
            throw new RuntimeException("Remaining elements incorrect in deque");
        }
    }

    static void testDrainToFullDrain() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("alpha");
        deque.add("beta");
        ArrayList<String> target = new ArrayList<>();
        int drained = deque.drainTo(target, 10);
        if (drained != 2) {
            throw new RuntimeException("Expected 2 elements drained, got " + drained);
        }
        if (target.size() != 2 || !target.get(0).equals("alpha") || !target.get(1).equals("beta")) {
            throw new RuntimeException("Drained elements incorrect: " + target);
        }
        if (!deque.isEmpty()) {
            throw new RuntimeException("Deque should be empty after full drain");
        }
    }
}
