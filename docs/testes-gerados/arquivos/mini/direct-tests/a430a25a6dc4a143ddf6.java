/*
 * @test
 * @summary Test ConcurrentHashMap tryAdvance method behavior
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceOnEmptyMap();
        testTryAdvanceOnNonEmptyMap();
    }

    static void testTryAdvanceWithNullAction() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        ConcurrentHashMap<Integer, String>.ValueSpliterator spliterator = map.new ValueSpliterator(0, 0, null, 0, 0);
        try {
            spliterator.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testTryAdvanceOnEmptyMap() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        ConcurrentHashMap<Integer, String>.ValueSpliterator spliterator = map.new ValueSpliterator(0, 0, null, 0, 0);
        boolean advanced = spliterator.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new RuntimeException("Should not be called on empty map");
            }
        });
        if (advanced) {
            throw new RuntimeException("tryAdvance should return false on empty map");
        }
    }

    static void testTryAdvanceOnNonEmptyMap() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "one");
        map.put(2, "two");

        ConcurrentHashMap<Integer, String>.ValueSpliterator spliterator = map.new ValueSpliterator(0, 0, null, 0, 0);

        final StringBuilder collected = new StringBuilder();
        boolean advanced = spliterator.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                collected.append(s);
            }
        });

        if (!advanced) {
            throw new RuntimeException("tryAdvance should return true when element is present");
        }

        if (collected.length() == 0) {
            throw new RuntimeException("Consumer accept should be called with a value");
        }
    }
}
