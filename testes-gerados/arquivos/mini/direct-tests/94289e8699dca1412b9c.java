/*
 * @test
 * @summary Test java.util.WeakHashMap.tryAdvance(Consumer) method behavior
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        testNullActionThrows();
        testTryAdvanceReturnsFalseOnEmptyMap();
        testTryAdvanceReturnsTrueAndAcceptsValue();
        testTryAdvanceThrowsConcurrentModificationException();
    }

    static void testNullActionThrows() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        try {
            map.tryAdvance(null);
            throw new Error("Expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testTryAdvanceReturnsFalseOnEmptyMap() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        boolean advanced = map.tryAdvance(new Consumer<String>() {
            public void accept(String v) {
                throw new Error("Should not be called on empty map");
            }
        });
        if (advanced) {
            throw new Error("tryAdvance should return false on empty map");
        }
    }

    static void testTryAdvanceReturnsTrueAndAcceptsValue() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        Object key = new Object();
        String value = "value";
        map.put(key, value);

        final boolean[] accepted = {false};
        boolean advanced = map.tryAdvance(new Consumer<String>() {
            public void accept(String v) {
                if (!value.equals(v)) {
                    throw new Error("Consumer received wrong value");
                }
                accepted[0] = true;
            }
        });

        if (!advanced) {
            throw new Error("tryAdvance should return true when element present");
        }
        if (!accepted[0]) {
            throw new Error("Consumer was not called");
        }
    }

    static void testTryAdvanceThrowsConcurrentModificationException() {
        WeakHashMap<Object, String> map = new WeakHashMap<>();
        Object key = new Object();
        String value = "value";
        map.put(key, value);

        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String v) {
                // Modify map during iteration to cause ConcurrentModificationException
                map.put(new Object(), "other");
            }
        };

        try {
            map.tryAdvance(consumer);
            throw new Error("Expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
        }
    }
}
