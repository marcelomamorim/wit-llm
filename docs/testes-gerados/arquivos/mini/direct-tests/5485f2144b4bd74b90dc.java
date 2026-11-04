/*
 * @test
 * @summary Test LinkedBlockingDeque.clear() method behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) throws Exception {
        testClearOnEmptyDeque();
        testClearOnNonEmptyDeque();
    }

    static void testClearOnEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        // Clear on empty deque should not throw and leave it empty
        deque.clear();
        if (deque.size() != 0) {
            throw new AssertionError("Expected size 0 after clear on empty deque, got " + deque.size());
        }
    }

    static void testClearOnNonEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("one");
        deque.add("two");
        deque.add("three");
        if (deque.size() != 3) {
            throw new AssertionError("Setup failed: expected size 3, got " + deque.size());
        }
        deque.clear();
        if (deque.size() != 0) {
            throw new AssertionError("Expected size 0 after clear on non-empty deque, got " + deque.size());
        }
        // After clear, deque should not contain any elements
        if (deque.contains("one") || deque.contains("two") || deque.contains("three")) {
            throw new AssertionError("Deque still contains elements after clear");
        }
    }
}
