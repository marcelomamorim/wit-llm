/*
 * @test
 * @summary Test ConcurrentHashMap.tryAdvance behavior including null action exception
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceWithEmptyMap();
        testTryAdvanceWithOneEntry();
    }

    static void testTryAdvanceWithNullAction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        ConcurrentHashMap.ValueSpliterator<String, String> spliterator = map.new ValueSpliterator<>(map.table, 0, map.table.length, map.size());
        boolean npeThrown = false;
        try {
            spliterator.tryAdvance(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    static void testTryAdvanceWithEmptyMap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        ConcurrentHashMap.ValueSpliterator<String, String> spliterator = map.new ValueSpliterator<>(map.table, 0, map.table.length, map.size());
        boolean advanced = spliterator.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new AssertionError("Should not be called on empty map");
            }
        });
        if (advanced) {
            throw new AssertionError("tryAdvance should return false on empty map");
        }
    }

    static void testTryAdvanceWithOneEntry() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "value");
        ConcurrentHashMap.ValueSpliterator<String, String> spliterator = map.new ValueSpliterator<>(map.table, 0, map.table.length, map.size());
        final boolean[] called = {false};
        boolean advanced = spliterator.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                if (!"value".equals(s)) {
                    throw new AssertionError("Expected value 'value', got: " + s);
                }
                called[0] = true;
            }
        });
        if (!advanced) {
            throw new AssertionError("tryAdvance should return true when element is present");
        }
        if (!called[0]) {
            throw new AssertionError("Consumer.accept was not called");
        }
    }
}
