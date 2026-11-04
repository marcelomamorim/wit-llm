/*
 * @test
 * @summary Verify Collections unmodifiable entry-set spliterator forEachRemaining null check, ordering and entry immutability
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testVisitsEntriesInOrder();
        testEntriesAreUnmodifiable();
        testEmptyMapDoesNotInvokeAction();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        Map<String, Integer> m = new LinkedHashMap<>();
        m.put("a", 1);
        Spliterator<Map.Entry<String, Integer>> sp =
                Collections.unmodifiableMap(m).entrySet().spliterator();
        boolean thrown = false;
        try {
            sp.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEachRemaining(null) must throw NullPointerException");
    }

    static void testVisitsEntriesInOrder() {
        Map<String, Integer> m = new LinkedHashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        Spliterator<Map.Entry<String, Integer>> sp =
                Collections.unmodifiableMap(m).entrySet().spliterator();
        List<String> keys = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        sp.forEachRemaining(e -> {
            keys.add(e.getKey());
            values.add(e.getValue());
        });
        check(keys.equals(java.util.Arrays.asList("a", "b", "c")),
                "unexpected key order: " + keys);
        check(values.equals(java.util.Arrays.asList(1, 2, 3)),
                "unexpected values: " + values);
    }

    static void testEntriesAreUnmodifiable() {
        Map<String, Integer> m = new LinkedHashMap<>();
        m.put("k", 10);
        Spliterator<Map.Entry<String, Integer>> sp =
                Collections.unmodifiableMap(m).entrySet().spliterator();
        boolean[] threw = new boolean[1];
        sp.forEachRemaining(e -> {
            try {
                e.setValue(99);
            } catch (UnsupportedOperationException ex) {
                threw[0] = true;
            }
        });
        check(threw[0], "entry passed to action must reject setValue");
        check(m.get("k") == 10, "underlying map must not change");
    }

    static void testEmptyMapDoesNotInvokeAction() {
        Map<String, Integer> m = new LinkedHashMap<>();
        Spliterator<Map.Entry<String, Integer>> sp =
                Collections.unmodifiableMap(m).entrySet().spliterator();
        boolean[] called = new boolean[1];
        sp.forEachRemaining(e -> called[0] = true);
        check(!called[0], "action must not run for empty map");
    }
}
