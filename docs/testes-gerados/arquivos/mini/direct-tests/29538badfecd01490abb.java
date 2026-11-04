/*
 * @test
 * @summary Test java.util.WeakHashMap tryAdvance method behavior
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.Map;
import java.util.ConcurrentModificationException;
import java.util.AbstractMap;
import java.util.function.Consumer;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrows();
        testTryAdvanceReturnsFalseOnEmptyMap();
        testTryAdvanceReturnsTrueAndProcessesEntry();
        testTryAdvanceThrowsConcurrentModificationException();
    }

    static void testNullActionThrows() {
        WeakHashMap<Object, Object> map = new WeakHashMap<>();
        try {
            map.tryAdvance(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testTryAdvanceReturnsFalseOnEmptyMap() {
        WeakHashMap<Object, Object> map = new WeakHashMap<>();
        boolean advanced = map.tryAdvance(new Consumer<Map.Entry<Object, Object>>() {
            public void accept(Map.Entry<Object, Object> e) {
                throw new AssertionError("Should not be called on empty map");
            }
        });
        if (advanced) {
            throw new AssertionError("tryAdvance should return false on empty map");
        }
    }

    static void testTryAdvanceReturnsTrueAndProcessesEntry() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key", "value");

        final boolean[] called = {false};
        boolean advanced = map.tryAdvance(new Consumer<Map.Entry<String, String>>() {
            public void accept(Map.Entry<String, String> e) {
                if (!"key".equals(e.getKey()) || !"value".equals(e.getValue())) {
                    throw new AssertionError("Entry key or value mismatch");
                }
                called[0] = true;
            }
        });

        if (!advanced) {
            throw new AssertionError("tryAdvance should return true when entry present");
        }
        if (!called[0]) {
            throw new AssertionError("Consumer accept method was not called");
        }
    }

    static void testTryAdvanceThrowsConcurrentModificationException() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key", "value");

        try {
            map.tryAdvance(new Consumer<Map.Entry<String, String>>() {
                public void accept(Map.Entry<String, String> e) {
                    // Modify map during iteration to cause ConcurrentModificationException
                    map.put("anotherKey", "anotherValue");
                }
            });
            throw new AssertionError("Expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
        }
    }
}
