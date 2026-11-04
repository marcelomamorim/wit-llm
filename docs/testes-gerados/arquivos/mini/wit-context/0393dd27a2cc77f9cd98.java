/*
 * @test
 * @summary Test LinkedBlockingDeque.drainTo(Collection<? super E>) for NPE on null argument
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.Collection;
import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        // Test that drainTo with null argument throws NullPointerException
        try {
            deque.drainTo(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test that drainTo with empty collection returns 0
        Collection<String> target = new ArrayList<>();
        int drained = deque.drainTo(target);
        if (drained != 0) {
            throw new RuntimeException("Expected drainTo to return 0 on empty deque");
        }

        // Test that drainTo transfers elements correctly
        deque.add("one");
        deque.add("two");
        target.clear();
        drained = deque.drainTo(target);
        if (drained != 2) {
            throw new RuntimeException("Expected drainTo to return 2 after draining two elements");
        }
        if (target.size() != 2 || !target.contains("one") || !target.contains("two")) {
            throw new RuntimeException("Expected target collection to contain drained elements");
        }

        // After draining, deque should be empty
        if (!deque.isEmpty()) {
            throw new RuntimeException("Expected deque to be empty after drainTo");
        }
    }
}
