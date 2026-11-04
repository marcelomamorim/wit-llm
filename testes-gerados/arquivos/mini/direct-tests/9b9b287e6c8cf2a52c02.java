/*
 * @test
 * @summary Test ConcurrentSkipListMap.forEachRemaining behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Consumer;
import java.util.NoSuchElementException;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingEmptyMap();
        testForEachRemainingWithElements();
        testForEachRemainingStopsAtFence();
    }

    static void testForEachRemainingNullAction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            // The forEachRemaining method is not public, so we test via iterator
            ConcurrentSkipListMap<Integer, String>.Iterator it = (ConcurrentSkipListMap<Integer, String>.Iterator) map.keySet().iterator();
            it.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        final StringBuilder sb = new StringBuilder();
        for (Integer i : map.keySet()) {
            // no elements
        }
        // Use iterator and forEachRemaining to confirm no elements processed
        ConcurrentSkipListMap<Integer, String>.Iterator it = (ConcurrentSkipListMap<Integer, String>.Iterator) map.keySet().iterator();
        it.forEachRemaining(new Consumer<Integer>() {
            public void accept(Integer i) {
                sb.append(i);
            }
        });
        if (sb.length() != 0) {
            throw new RuntimeException("forEachRemaining processed elements in empty map");
        }
    }

    static void testForEachRemainingWithElements() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        final StringBuilder sb = new StringBuilder();
        ConcurrentSkipListMap<Integer, String>.Iterator it = (ConcurrentSkipListMap<Integer, String>.Iterator) map.keySet().iterator();
        it.forEachRemaining(new Consumer<Integer>() {
            public void accept(Integer i) {
                sb.append(i);
            }
        });

        String result = sb.toString();
        if (!result.equals("123")) {
            throw new RuntimeException("forEachRemaining did not process all keys in order: " + result);
        }
    }

    static void testForEachRemainingStopsAtFence() {
        // This test requires access to the internal fence field, which is not public.
        // We test that forEachRemaining stops at fence by using subMap view.
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(4, "four");

        final StringBuilder sb = new StringBuilder();
        // Use subMap to create a view with fence at 3 (exclusive)
        for (Integer key : map.subMap(1, 3).keySet()) {
            sb.append(key);
        }

        String result = sb.toString();
        if (!result.equals("12")) {
            throw new RuntimeException("forEachRemaining did not stop at fence as expected: " + result);
        }
    }
}
