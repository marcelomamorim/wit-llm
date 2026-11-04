/*
 * @test
 * @summary Witup-derived checks for Collections.unmodifiableMap(...).entrySet().forEach(action)
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    static int failures = 0;

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testForEachVisitsAllEntries();
        testEntriesAreUnmodifiable();
        if (failures > 0) {
            throw new RuntimeException("Failures: " + failures);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + message);
        }
    }

    static void testNullActionThrowsNpe() {
        Map<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        Map<String, Integer> um = Collections.unmodifiableMap(m);
        boolean thrown = false;
        try {
            um.entrySet().forEach((Consumer<Map.Entry<String, Integer>>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) on unmodifiable entrySet must throw NullPointerException");
    }

    static void testForEachVisitsAllEntries() {
        Map<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        Map<String, Integer> um = Collections.unmodifiableMap(m);
        final int[] sum = {0};
        final int[] count = {0};
        um.entrySet().forEach(e -> {
            sum[0] += e.getValue();
            count[0]++;
        });
        check(count[0] == 3, "forEach must visit 3 entries, got " + count[0]);
        check(sum[0] == 6, "sum of values must be 6, got " + sum[0]);
    }

    static void testEntriesAreUnmodifiable() {
        Map<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        Map<String, Integer> um = Collections.unmodifiableMap(m);
        final boolean[] threw = {false};
        um.entrySet().forEach(e -> {
            try {
                e.setValue(99);
            } catch (UnsupportedOperationException ex) {
                threw[0] = true;
            }
        });
        check(threw[0], "entries passed to forEach must reject setValue with UnsupportedOperationException");
        check(m.get("a") == 1, "underlying map must not be modified through forEach entries");
    }
}
