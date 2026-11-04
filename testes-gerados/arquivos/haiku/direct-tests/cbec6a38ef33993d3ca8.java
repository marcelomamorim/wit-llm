/*
 * @test
 * @summary Verifies forEachRemaining on the spliterator of an unmodifiable map entry set returns unmodifiable entries and rejects null actions
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static Map<String, Integer> sampleMap() {
        Map<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        return m;
    }

    public static void main(String[] args) {
        testForEachRemainingVisitsAllEntries();
        testForEachRemainingVisitsOnlyRemainingEntries();
        testEntriesAreUnmodifiable();
        testNullActionThrowsNPE();
    }

    static void testForEachRemainingVisitsAllEntries() {
        Map<String, Integer> m = sampleMap();
        Spliterator<Map.Entry<String, Integer>> sp =
                Collections.unmodifiableMap(m).entrySet().spliterator();
        final int[] count = {0};
        final int[] sum = {0};
        sp.forEachRemaining(e -> {
            count[0]++;
            sum[0] += e.getValue();
        });
        check(count[0] == 3, "expected 3 entries, got " + count[0]);
        check(sum[0] == 6, "expected value sum 6, got " + sum[0]);
    }

    static void testForEachRemainingVisitsOnlyRemainingEntries() {
        Map<String, Integer> m = sampleMap();
        Spliterator<Map.Entry<String, Integer>> sp =
                Collections.unmodifiableMap(m).entrySet().spliterator();
        boolean advanced = sp.tryAdvance(e -> { });
        check(advanced, "tryAdvance should consume one entry");
        final int[] count = {0};
        sp.forEachRemaining(e -> count[0]++);
        check(count[0] == 2, "expected 2 remaining entries, got " + count[0]);
    }

    static void testEntriesAreUnmodifiable() {
        Map<String, Integer> m = sampleMap();
        Spliterator<Map.Entry<String, Integer>> sp =
                Collections.unmodifiableMap(m).entrySet().spliterator();
        final boolean[] threw = {false};
        sp.forEachRemaining(e -> {
            try {
                e.setValue(99);
            } catch (UnsupportedOperationException ex) {
                threw[0] = true;
            }
        });
        check(threw[0], "setValue on entry passed to forEachRemaining should throw UnsupportedOperationException");
        for (Integer v : m.values()) {
            check(v != 99, "backing map must not be modified");
        }
    }

    static void testNullActionThrowsNPE() {
        Map<String, Integer> m = sampleMap();
        Spliterator<Map.Entry<String, Integer>> sp =
                Collections.unmodifiableMap(m).entrySet().spliterator();
        boolean npe = false;
        try {
            sp.forEachRemaining(null);
        } catch (NullPointerException ex) {
            npe = true;
        }
        check(npe, "forEachRemaining(null) should throw NullPointerException");
    }
}
