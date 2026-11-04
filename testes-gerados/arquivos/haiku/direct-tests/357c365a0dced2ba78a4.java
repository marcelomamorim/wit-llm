/*
 * @test
 * @summary Verifies forEach on the unmodifiable entry set returned by Collections.unmodifiableMap
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class CollectionsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testForEachVisitsAllEntriesInOrder() {
        Map<String, Integer> source = new LinkedHashMap<>();
        source.put("a", 1);
        source.put("b", 2);
        source.put("c", 3);
        Map<String, Integer> view = Collections.unmodifiableMap(source);
        Set<Map.Entry<String, Integer>> entries = view.entrySet();

        final StringBuilder keys = new StringBuilder();
        final int[] sum = new int[1];
        entries.forEach(e -> {
            keys.append(e.getKey());
            sum[0] += e.getValue();
        });

        check(keys.toString().equals("abc"), "unexpected iteration order: " + keys);
        check(sum[0] == 6, "unexpected value sum: " + sum[0]);
    }

    static void testForEachOnEmptyMapDoesNotInvokeAction() {
        Map<String, Integer> view = Collections.unmodifiableMap(new LinkedHashMap<String, Integer>());
        final int[] calls = new int[1];
        view.entrySet().forEach(e -> calls[0]++);
        check(calls[0] == 0, "action should not be invoked for empty map");
    }

    static void testEntriesFromForEachAreReadOnly() {
        Map<String, Integer> source = new LinkedHashMap<>();
        source.put("k", 10);
        Map<String, Integer> view = Collections.unmodifiableMap(source);
        final boolean[] threw = new boolean[1];
        view.entrySet().forEach(e -> {
            try {
                e.setValue(99);
            } catch (UnsupportedOperationException ex) {
                threw[0] = true;
            }
        });
        check(threw[0], "setValue on entry passed to forEach should throw UnsupportedOperationException");
        check(source.get("k") == 10, "underlying map must not be modified");
    }

    static void testForEachNullActionThrowsNpe() {
        Map<String, Integer> source = new LinkedHashMap<>();
        source.put("x", 1);
        Set<Map.Entry<String, Integer>> entries = Collections.unmodifiableMap(source).entrySet();
        boolean npe = false;
        try {
            entries.forEach(null);
        } catch (NullPointerException ex) {
            npe = true;
        }
        check(npe, "forEach(null) should throw NullPointerException");
    }

    public static void main(String[] args) {
        testForEachVisitsAllEntriesInOrder();
        testForEachOnEmptyMapDoesNotInvokeAction();
        testEntriesFromForEachAreReadOnly();
        testForEachNullActionThrowsNpe();
    }
}
