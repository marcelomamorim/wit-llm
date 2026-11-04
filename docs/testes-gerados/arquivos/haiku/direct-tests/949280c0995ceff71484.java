/*
 * @test
 * @summary Tests ConcurrentHashMap.entrySet().forEach(Consumer) visiting entries and rejecting null actions
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testEmptyMapNoActionCalls();
        testVisitsAllEntriesOnce();
        testEntryKeyAndValue();
        testNullActionThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyMapNoActionCalls() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        final int[] count = {0};
        map.entrySet().forEach(e -> count[0]++);
        check(count[0] == 0, "action must not be called for empty map, count=" + count[0]);
    }

    static void testVisitsAllEntriesOnce() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 100; i++) {
            map.put("k" + i, i);
        }
        final List<String> visitedKeys = new ArrayList<>();
        final HashMap<String, Integer> seen = new HashMap<>();
        map.entrySet().forEach(e -> {
            visitedKeys.add(e.getKey());
            seen.put(e.getKey(), e.getValue());
        });
        check(visitedKeys.size() == 100, "expected 100 visits, got " + visitedKeys.size());
        check(seen.size() == 100, "expected 100 distinct keys, got " + seen.size());
        for (int i = 0; i < 100; i++) {
            Integer v = seen.get("k" + i);
            check(v != null && v == i, "wrong value for k" + i + ": " + v);
        }
    }

    static void testEntryKeyAndValue() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        final List<Map.Entry<String, Integer>> entries = new ArrayList<>();
        map.entrySet().forEach(e -> entries.add(e));
        check(entries.size() == 1, "expected one entry, got " + entries.size());
        check("a".equals(entries.get(0).getKey()), "wrong key: " + entries.get(0).getKey());
        check(Integer.valueOf(1).equals(entries.get(0).getValue()), "wrong value: " + entries.get(0).getValue());
    }

    static void testNullActionThrowsNPE() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        Consumer<Map.Entry<String, Integer>> nullAction = null;
        boolean thrown = false;
        try {
            map.entrySet().forEach(nullAction);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }
}
