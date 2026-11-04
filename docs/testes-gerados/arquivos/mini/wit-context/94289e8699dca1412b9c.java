/*
 * @test
 * @summary Test WeakHashMap.tryAdvance behavior including null action and normal iteration
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.function.Consumer;
import java.util.ConcurrentModificationException;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceNormalIteration();
        testTryAdvanceConcurrentModification();
    }

    static void testTryAdvanceNullAction() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        WeakHashMap.ValueSpliterator<String> spliterator = new WeakHashMap.ValueSpliterator<>(map, 0, 0, 0, map.modCount);
        try {
            spliterator.tryAdvance(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testTryAdvanceNormalIteration() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        Object key1 = new Object();
        Object key2 = new Object();
        map.put(key1, "one");
        map.put(key2, "two");

        WeakHashMap.ValueSpliterator<String> spliterator = new WeakHashMap.ValueSpliterator<>(map, 0, map.table.length, 0, map.modCount);

        final StringBuilder collected = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                collected.append(s).append(",");
            }
        };

        boolean advanced1 = spliterator.tryAdvance(action);
        boolean advanced2 = spliterator.tryAdvance(action);
        boolean advanced3 = spliterator.tryAdvance(action);

        if (!advanced1 || !advanced2) {
            throw new AssertionError("Expected at least two elements to be iterated");
        }
        if (advanced3) {
            throw new AssertionError("Expected no more elements after two advances");
        }

        String result = collected.toString();
        if (!(result.contains("one") && result.contains("two"))) {
            throw new AssertionError("Expected collected values to contain 'one' and 'two', got: " + result);
        }
    }

    static void testTryAdvanceConcurrentModification() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        Object key = new Object();
        map.put(key, "value");

        WeakHashMap.ValueSpliterator<String> spliterator = new WeakHashMap.ValueSpliterator<>(map, 0, map.table.length, 0, map.modCount);

        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                // Modify map during iteration to trigger ConcurrentModificationException
                map.put(new Object(), "newValue");
            }
        };

        try {
            spliterator.tryAdvance(action);
            throw new AssertionError("Expected ConcurrentModificationException due to modification during iteration");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
