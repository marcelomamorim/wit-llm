/*
 * @test
 * @summary Test HashMap.forEachRemaining(Consumer) behavior
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class HashMapWitupTest {
    public static void main(String[] args) {
        testNullActionThrows();
        testForEachRemainingIteratesAllKeys();
        testForEachRemainingConcurrentModification();
        testForEachRemainingEmptyIterator();
    }

    static void testNullActionThrows() {
        HashMap<String, String> map = new HashMap<>();
        Iterator<String> it = map.keySet().iterator();
        try {
            it.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
        }
    }

    static void testForEachRemainingIteratesAllKeys() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        Iterator<String> it = map.keySet().iterator();
        final StringBuilder sb = new StringBuilder();
        it.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        });

        String result = sb.toString();
        // The keys iterated should be exactly the keys in the map
        for (String key : map.keySet()) {
            if (!result.contains(key + ",")) {
                throw new RuntimeException("Missing key in forEachRemaining: " + key);
            }
        }
        // Also check that at least one key was iterated
        if (result.isEmpty()) {
            throw new RuntimeException("forEachRemaining did not iterate any keys");
        }
    }

    static void testForEachRemainingConcurrentModification() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);

        Iterator<String> it = map.keySet().iterator();

        // Modify map after iterator creation to cause ConcurrentModificationException
        map.put("c", 3);

        try {
            it.forEachRemaining(new Consumer<String>() {
                public void accept(String s) {
                    // do nothing
                }
            });
            throw new RuntimeException("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
        }
    }

    static void testForEachRemainingEmptyIterator() {
        HashMap<String, String> map = new HashMap<>();
        Iterator<String> it = map.keySet().iterator();
        final boolean[] called = {false};
        it.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                called[0] = true;
            }
        });
        if (called[0]) {
            throw new RuntimeException("forEachRemaining called consumer on empty iterator");
        }
    }
}
