/*
 * @test
 * @summary Verify that put on immutable maps from the public Map.of and Map.ofEntries factories throws UnsupportedOperationException and leaves the map unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        testPutOnSingleEntryMapOf();
        testPutOnEmptyMapOf();
        testPutOnMapOfEntries();
        testPutWithNullArgumentsStillThrowsUoe();
    }

    static void testPutOnSingleEntryMapOf() {
        Map<String, Integer> m = Map.of("a", 1);
        expectUoe(m, "b", 2);
        if (m.size() != 1 || m.get("a").intValue() != 1 || m.containsKey("b")) {
            throw new AssertionError("map changed after rejected put");
        }
    }

    static void testPutOnEmptyMapOf() {
        Map<String, Integer> m = Map.of();
        expectUoe(m, "x", 1);
        if (!m.isEmpty()) {
            throw new AssertionError("empty map changed after rejected put");
        }
    }

    static void testPutOnMapOfEntries() {
        Map<String, Integer> m = Map.ofEntries(Map.entry("k1", 1), Map.entry("k2", 2));
        expectUoe(m, "k1", 10);
        if (m.size() != 2 || m.get("k1").intValue() != 1 || m.get("k2").intValue() != 2) {
            throw new AssertionError("map changed after rejected put");
        }
    }

    static void testPutWithNullArgumentsStillThrowsUoe() {
        Map<String, Integer> m = Map.of("a", 1);
        expectUoe(m, null, null);
    }

    static void expectUoe(Map<String, Integer> m, String k, Integer v) {
        try {
            m.put(k, v);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new AssertionError("expected UnsupportedOperationException from put");
    }
}
