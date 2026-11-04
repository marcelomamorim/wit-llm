/*
 * @test
 * @summary Test PriorityQueue.retainAll(Collection<?>) for null argument and basic behavior
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PriorityQueueWitupTest {
    public static void main(String[] args) {
        testRetainAllNullArgument();
        testRetainAllBasicBehavior();
    }

    private static void testRetainAllNullArgument() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        try {
            pq.retainAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRetainAllBasicBehavior() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);

        Collection<Integer> retain = new ArrayList<>();
        retain.add(2);
        retain.add(3);

        boolean changed = pq.retainAll(retain);
        if (!changed) {
            throw new RuntimeException("retainAll should return true when elements are removed");
        }

        // After retainAll, pq should contain only 2 and 3
        if (pq.size() != 2 || !pq.contains(2) || !pq.contains(3)) {
            throw new RuntimeException("PriorityQueue contents incorrect after retainAll");
        }

        // Calling retainAll with a collection containing all elements should return false
        changed = pq.retainAll(retain);
        if (changed) {
            throw new RuntimeException("retainAll should return false when no elements are removed");
        }
    }
}
