/* @test    @summary Test drainTo method of LinkedBlockingDeque for correct behavior and regression protection    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.Collection;
import java.util.ArrayList;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testDrainToEmptyDeque();
        testDrainToWithElements();
        testDrainToWithLimitedCapacity();
    }

    static void testDrainToEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        Collection<Integer> c = new ArrayList<>();
        int drainedCount = deque.drainTo(c);
        if (drainedCount != 0) {
            throw new AssertionError("Expected drained count 0 for empty deque, got " + drainedCount);
        }
        if (!c.isEmpty()) {
            throw new AssertionError("Collection should be empty after draining from empty deque");
        }
    }

    static void testDrainToWithElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        Collection<String> c = new ArrayList<>();
        int drainedCount = deque.drainTo(c);
        if (drainedCount != 3) {
            throw new AssertionError("Expected drained count 3, got " + drainedCount);
        }
        if (c.size() != 3 || !c.contains("a") || !c.contains("b") || !c.contains("c")) {
            throw new AssertionError("Collection does not contain expected elements after drain: " + c);
        }
        if (deque.size() != 0) {
            throw new AssertionError("Deque should be empty after drain, size: " + deque.size());
        }
    }

    static void testDrainToWithLimitedCapacity() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        Collection<Integer> c = new ArrayList<>();
        int drainedCount = deque.drainTo(c, 2);
        if (drainedCount != 2) {
            throw new AssertionError("Expected drained count 2, got " + drainedCount);
        }
        if (c.size() != 2 || !c.contains(1) || !c.contains(2)) {
            throw new AssertionError("Collection does not contain expected elements after limited drain: " + c);
        }
        if (deque.size() != 1) {
            throw new AssertionError("Deque should have 1 element remaining, size: " + deque.size());
        }
        if (deque.peek() != 3) {
            throw new AssertionError("Remaining element should be 3, but was: " + deque.peek());
        }
    }
}
