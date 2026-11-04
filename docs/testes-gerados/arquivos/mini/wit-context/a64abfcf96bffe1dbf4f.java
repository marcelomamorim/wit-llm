/*
 * @test
 * @summary Test TreeMap.tryAdvance(Consumer) for expected behavior and exceptions
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceEmptyMap();
        testTryAdvanceNormalUsage();
        testTryAdvanceConcurrentModification();
    }

    static void testTryAdvanceNullAction() {
        TreeMap<Integer, String> map = new TreeMap<>();
        TreeMap<Integer, String>.DescendingKeySpliterator spliterator = map.descendingKeySet().spliterator();
        try {
            spliterator.tryAdvance(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testTryAdvanceEmptyMap() {
        TreeMap<Integer, String> map = new TreeMap<>();
        TreeMap<Integer, String>.DescendingKeySpliterator spliterator = map.descendingKeySet().spliterator();
        boolean advanced = spliterator.tryAdvance(new Consumer<Integer>() {
            public void accept(Integer i) {
                throw new AssertionError("Should not be called on empty map");
            }
        });
        if (advanced) {
            throw new AssertionError("tryAdvance should return false on empty map");
        }
    }

    static void testTryAdvanceNormalUsage() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        TreeMap<Integer, String>.DescendingKeySpliterator spliterator = map.descendingKeySet().spliterator();
        final int[] count = {0};
        boolean advanced = spliterator.tryAdvance(new Consumer<Integer>() {
            public void accept(Integer i) {
                if (i == null) {
                    throw new AssertionError("Key should not be null");
                }
                count[0]++;
            }
        });
        if (!advanced) {
            throw new AssertionError("tryAdvance should return true when element is present");
        }
        if (count[0] != 1) {
            throw new AssertionError("Consumer accept should be called exactly once");
        }
    }

    static void testTryAdvanceConcurrentModification() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        TreeMap<Integer, String>.DescendingKeySpliterator spliterator = map.descendingKeySet().spliterator();
        // Modify map structurally after spliterator creation to trigger ConcurrentModificationException
        map.put(2, "two");
        try {
            spliterator.tryAdvance(new Consumer<Integer>() {
                public void accept(Integer i) {
                    // do nothing
                }
            });
            throw new AssertionError("Expected ConcurrentModificationException due to structural modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
