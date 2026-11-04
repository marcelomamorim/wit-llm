/*
 * @test
 * @summary Test ConcurrentSkipListMap.forEach with valid and null BiConsumer
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testForEachWithEntries();
        testForEachWithEmptyMap();
        testForEachWithNullAction();
    }

    static void testForEachWithEntries() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        final AtomicInteger count = new AtomicInteger(0);
        BiConsumer<Integer, String> action = new BiConsumer<Integer, String>() {
            public void accept(Integer k, String v) {
                if (k == null || v == null) {
                    throw new AssertionError("Key or value is null");
                }
                count.incrementAndGet();
            }
        };

        map.forEach(action);

        if (count.get() != 3) {
            throw new AssertionError("forEach did not visit all entries");
        }
    }

    static void testForEachWithEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        final AtomicInteger count = new AtomicInteger(0);
        BiConsumer<Integer, String> action = new BiConsumer<Integer, String>() {
            public void accept(Integer k, String v) {
                count.incrementAndGet();
            }
        };
        map.forEach(action);
        if (count.get() != 0) {
            throw new AssertionError("forEach on empty map should not invoke action");
        }
    }

    static void testForEachWithNullAction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
