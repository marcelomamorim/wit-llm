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
        WeakHashMap<Object, Object> map = new WeakHashMap<>();
        WeakHashMap<Object, Object>.KeySpliterator spliterator = map.new KeySpliterator(0, 0, map.modCount);
        try {
            spliterator.tryAdvance(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testTryAdvanceNormalIteration() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        final StringBuilder collected = new StringBuilder();
        WeakHashMap<String, String>.KeySpliterator spliterator = map.new KeySpliterator(0, map.table.length, map.modCount);

        boolean advanced = spliterator.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                collected.append(s).append(",");
            }
        });

        if (!advanced) {
            throw new AssertionError("Expected tryAdvance to return true when elements present");
        }

        if (collected.length() == 0) {
            throw new AssertionError("Expected consumer to be called with a key");
        }
    }

    static void testTryAdvanceConcurrentModification() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key", "value");

        final WeakHashMap<String, String>.KeySpliterator spliterator = map.new KeySpliterator(0, map.table.length, map.modCount);

        try {
            spliterator.tryAdvance(new Consumer<String>() {
                public void accept(String s) {
                    // Modify map during iteration to trigger ConcurrentModificationException
                    map.put("anotherKey", "anotherValue");
                }
            });
            throw new AssertionError("Expected ConcurrentModificationException due to concurrent modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
