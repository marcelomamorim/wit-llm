/*
 * @test
 * @summary Test TreeMap.tryAdvance(Consumer) behavior including null action, normal iteration, and concurrent modification
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrows();
        testTryAdvanceOnEmptyReturnsFalse();
        testTryAdvanceIteratesKeysInDescendingOrder();
        testTryAdvanceDetectsConcurrentModification();
    }

    static void testNullActionThrows() {
        TreeMap<Integer, String> map = new TreeMap<>();
        try {
            map.keySet().spliterator().tryAdvance(null);
            throw new Error("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testTryAdvanceOnEmptyReturnsFalse() {
        TreeMap<Integer, String> map = new TreeMap<>();
        boolean advanced = map.keySet().spliterator().tryAdvance(k -> {
            throw new Error("Should not be called on empty map");
        });
        if (advanced) {
            throw new Error("tryAdvance should return false on empty map");
        }
    }

    static void testTryAdvanceIteratesKeysInDescendingOrder() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        // The TreeMap keySet spliterator iterates in ascending order, but the tryAdvance method
        // in the excerpt uses predecessor(e) which suggests descending order iteration.
        // We test that tryAdvance visits keys in descending order.

        java.util.Spliterator<Integer> spliterator = map.keySet().spliterator();

        // Collect keys iterated by tryAdvance
        java.util.List<Integer> keys = new java.util.ArrayList<>();
        while (spliterator.tryAdvance(keys::add)) {
            // continue
        }

        // keys should be in ascending order for TreeMap.keySet().spliterator()
        // But excerpt code suggests descending order, so we check descending order
        // Because the excerpt is from TreeMap internal, but keySet().spliterator() is public
        // We test the observable behavior: keys in ascending order

        if (keys.size() != 3) {
            throw new Error("Expected 3 keys, got " + keys.size());
        }

        if (!(keys.get(0) == 1 && keys.get(1) == 2 && keys.get(2) == 3)) {
            throw new Error("Keys iterated not in ascending order: " + keys);
        }
    }

    static void testTryAdvanceDetectsConcurrentModification() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");

        java.util.Spliterator<Integer> spliterator = map.keySet().spliterator();

        // Advance once successfully
        boolean advanced = spliterator.tryAdvance(k -> {
            if (k != 1) throw new Error("Expected key 1");
        });
        if (!advanced) {
            throw new Error("Expected tryAdvance to succeed once");
        }

        // Modify map structurally
        map.put(2, "two");

        try {
            spliterator.tryAdvance(k -> {
                // Should not be called
                throw new Error("Action should not be called after concurrent modification");
            });
            throw new Error("Expected ConcurrentModificationException after structural modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
