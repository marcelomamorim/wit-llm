/*
 * @test
 * @summary WeakHashMap entry spliterator tryAdvance rejects a null action and advances over live entries
 * @run main WeakHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.WeakHashMap;
import java.util.function.Consumer;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testNullActionThrowsNpeOnEmptyMap();
        testAdvanceDeliversEntryAndReturnsTrue();
        testAdvanceOnEmptyMapReturnsFalse();
    }

    static void testNullActionThrowsNpe() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("k", "v");
        Spliterator<Map.Entry<String, String>> s = map.entrySet().spliterator();
        try {
            s.tryAdvance(null);
            throw new AssertionError("NullPointerException expected for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullActionThrowsNpeOnEmptyMap() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        Spliterator<Map.Entry<String, String>> s = map.entrySet().spliterator();
        try {
            s.tryAdvance(null);
            throw new AssertionError("NullPointerException expected for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testAdvanceDeliversEntryAndReturnsTrue() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("k", "v");
        Spliterator<Map.Entry<String, String>> s = map.entrySet().spliterator();
        final List<String> seen = new ArrayList<>();
        Consumer<Map.Entry<String, String>> c =
            e -> seen.add(e.getKey() + "=" + e.getValue());
        if (!s.tryAdvance(c)) {
            throw new AssertionError("tryAdvance should return true for a live entry");
        }
        if (seen.size() != 1 || !"k=v".equals(seen.get(0))) {
            throw new AssertionError("unexpected entries: " + seen);
        }
        if (s.tryAdvance(c)) {
            throw new AssertionError("tryAdvance should return false after exhaustion");
        }
        if (seen.size() != 1) {
            throw new AssertionError("no additional entries expected: " + seen);
        }
    }

    static void testAdvanceOnEmptyMapReturnsFalse() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        Spliterator<Map.Entry<String, String>> s = map.entrySet().spliterator();
        final List<String> seen = new ArrayList<>();
        Consumer<Map.Entry<String, String>> c =
            e -> seen.add(e.getKey());
        if (s.tryAdvance(c)) {
            throw new AssertionError("tryAdvance should return false on empty map");
        }
        if (!seen.isEmpty()) {
            throw new AssertionError("action should not be invoked: " + seen);
        }
    }
}
