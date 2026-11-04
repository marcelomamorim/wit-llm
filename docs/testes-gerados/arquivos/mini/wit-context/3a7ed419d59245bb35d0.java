/*
 * @test
 * @summary Test PriorityQueue.removeIf(Predicate) for null argument and basic removal
 * @run main PriorityQueueWitupTest
 */

import java.util.PriorityQueue;
import java.util.function.Predicate;
import java.util.Objects;

public class PriorityQueueWitupTest {
    public static void main(String[] args) {
        testRemoveIfNullPredicate();
        testRemoveIfBasic();
    }

    private static void testRemoveIfNullPredicate() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        try {
            pq.removeIf(null);
            throw new Error("Expected NullPointerException for null predicate");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRemoveIfBasic() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.add("apple");
        pq.add("banana");
        pq.add("cherry");

        // Remove elements starting with 'b'
        boolean removed = pq.removeIf(new Predicate<String>() {
            public boolean test(String s) {
                return s.startsWith("b");
            }
        });

        if (!removed) {
            throw new Error("Expected removeIf to remove elements starting with 'b'");
        }

        if (pq.contains("banana")) {
            throw new Error("Element 'banana' should have been removed");
        }

        if (!pq.contains("apple") || !pq.contains("cherry")) {
            throw new Error("Elements 'apple' and 'cherry' should remain");
        }
    }
}
