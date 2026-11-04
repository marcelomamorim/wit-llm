/*
 * @test
 * @summary Verifies ConcurrentHashMap entry spliterator forEachRemaining null action handling and traversal via public API
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Map;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testVisitsAllEntries();
        testEmptyMapVisitsNothing();
    }

    static void testNullActionThrowsNPE() {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        map.put(1, 10);
        Spliterator<Map.Entry<Integer, Integer>> sp = map.entrySet().spliterator();
        boolean thrown = false;
        try {
            sp.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null action");
        }
    }

    static void testVisitsAllEntries() {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        map.put(1, 10);
        map.put(2, 20);
        map.put(3, 30);
        Spliterator<Map.Entry<Integer, Integer>> sp = map.entrySet().spliterator();
        final int[] count = {0};
        final long[] sum = {0L};
        final boolean[] bad = {false};
        sp.forEachRemaining(e -> {
            count[0]++;
            sum[0] += e.getValue();
            if (!e.getValue().equals(map.get(e.getKey()))) {
                bad[0] = true;
            }
        });
        if (count[0] != 3) {
            throw new RuntimeException("expected 3 entries visited but was " + count[0]);
        }
        if (sum[0] != 60L) {
            throw new RuntimeException("unexpected value sum " + sum[0]);
        }
        if (bad[0]) {
            throw new RuntimeException("visited entry value differs from map state");
        }
        if (sp.tryAdvance(e -> { })) {
            throw new RuntimeException("spliterator should be exhausted after forEachRemaining");
        }
    }

    static void testEmptyMapVisitsNothing() {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        Spliterator<Map.Entry<Integer, Integer>> sp = map.entrySet().spliterator();
        final int[] count = {0};
        sp.forEachRemaining(e -> count[0]++);
        if (count[0] != 0) {
            throw new RuntimeException("expected no entries for empty map but was " + count[0]);
        }
    }
}
