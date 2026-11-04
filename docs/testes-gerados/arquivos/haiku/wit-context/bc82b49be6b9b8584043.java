/*
 * @test
 * @summary Verify that putAll on immutable maps obtained from Map.of, Map.ofEntries and Map.copyOf throws UnsupportedOperationException and leaves the map unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.HashMap;
import java.util.Map;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testPutAllOnEmptyMapThrowsUoe();
        testPutAllOnNonEmptyMapThrowsUoeAndKeepsContent();
        testPutAllWithEmptySourceThrowsUoe();
        testPutAllOnMapOfEntriesThrowsUoe();
        testPutAllOnCopyOfThrowsUoe();
    }

    static void testPutAllOnEmptyMapThrowsUoe() {
        Map<String, Integer> empty = Map.of();
        Map<String, Integer> src = new HashMap<>();
        src.put("b", 2);
        expectUoe(() -> empty.putAll(src));
        check(empty.isEmpty(), "empty map must remain empty after failed putAll");
    }

    static void testPutAllOnNonEmptyMapThrowsUoeAndKeepsContent() {
        Map<String, Integer> one = Map.of("a", 1);
        Map<String, Integer> src = new HashMap<>();
        src.put("a", 99);
        src.put("c", 3);
        expectUoe(() -> one.putAll(src));
        check(one.size() == 1, "size changed after failed putAll: " + one.size());
        check(one.get("a") == 1, "value changed after failed putAll");
        check(!one.containsKey("c"), "new key added after failed putAll");
    }

    static void testPutAllWithEmptySourceThrowsUoe() {
        Map<String, Integer> one = Map.of("a", 1);
        Map<String, Integer> src = new HashMap<>();
        expectUoe(() -> one.putAll(src));
        check(one.size() == 1 && one.get("a") == 1, "map modified by putAll with empty source");
    }

    static void testPutAllOnMapOfEntriesThrowsUoe() {
        Map<String, Integer> m = Map.ofEntries(Map.entry("x", 10), Map.entry("y", 20));
        Map<String, Integer> src = new HashMap<>();
        src.put("z", 30);
        expectUoe(() -> m.putAll(src));
        check(m.size() == 2 && !m.containsKey("z"), "map modified after failed putAll");
    }

    static void testPutAllOnCopyOfThrowsUoe() {
        Map<String, Integer> base = new HashMap<>();
        base.put("k", 7);
        Map<String, Integer> copy = Map.copyOf(base);
        Map<String, Integer> src = new HashMap<>();
        src.put("j", 8);
        expectUoe(() -> copy.putAll(src));
        check(copy.size() == 1 && copy.get("k") == 7, "copyOf map modified after failed putAll");
    }

    interface Action {
        void run();
    }

    static void expectUoe(Action action) {
        try {
            action.run();
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException("Expected UnsupportedOperationException was not thrown");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
