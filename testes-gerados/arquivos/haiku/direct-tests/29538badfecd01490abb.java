/*
 * @test
 * @summary WeakHashMap entry spliterator tryAdvance: null action, empty map, full traversal, null key/value, and concurrent modification
 * @run main WeakHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyMapReturnsFalse();
        testIteratesAllEntries();
        testNullKeyAndNullValue();
        testConcurrentModificationDetected();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        Map<String, Integer> map = new WeakHashMap<>();
        String k1 = new String("a");
        map.put(k1, 1);
        Spliterator<Map.Entry<String, Integer>> sp = map.entrySet().spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) must throw NullPointerException");
        check(k1 != null, "key must stay reachable");
    }

    static void testEmptyMapReturnsFalse() {
        Map<String, Integer> map = new WeakHashMap<>();
        Spliterator<Map.Entry<String, Integer>> sp = map.entrySet().spliterator();
        final int[] calls = {0};
        boolean result = sp.tryAdvance(e -> calls[0]++);
        check(!result, "empty map must return false");
        check(calls[0] == 0, "action must not be invoked for empty map");
    }

    static void testIteratesAllEntries() {
        Map<String, Integer> map = new WeakHashMap<>();
        String k1 = new String("a");
        String k2 = new String("b");
        String k3 = new String("c");
        map.put(k1, 1);
        map.put(k2, 2);
        map.put(k3, 3);

        Spliterator<Map.Entry<String, Integer>> sp = map.entrySet().spliterator();
        List<String> seen = new ArrayList<>();
        int guard = 0;
        while (sp.tryAdvance(e -> seen.add(e.getKey() + "=" + e.getValue()))) {
            guard++;
            check(guard <= 10, "tryAdvance did not terminate");
        }
        check(seen.size() == 3, "expected 3 entries but got " + seen.size());
        check(seen.contains("a=1"), "missing a=1");
        check(seen.contains("b=2"), "missing b=2");
        check(seen.contains("c=3"), "missing c=3");

        check(!sp.tryAdvance(e -> { throw new AssertionError("unexpected"); }),
              "exhausted spliterator must return false");
    }

    static void testNullKeyAndNullValue() {
        Map<String, String> map = new WeakHashMap<>();
        String k = new String("x");
        map.put(null, null);
        map.put(k, null);
        Spliterator<Map.Entry<String, String>> sp = map.entrySet().spliterator();
        List<String> seen = new ArrayList<>();
        while (sp.tryAdvance(e -> seen.add(String.valueOf(e.getKey()) + "=" + String.valueOf(e.getValue())))) {
            // drain
        }
        check(seen.size() == 2, "expected 2 entries but got " + seen.size());
        check(seen.contains("null=null"), "null key entry missing");
        check(seen.contains("x=null"), "null value entry missing");
    }

    static void testConcurrentModificationDetected() {
        Map<String, Integer> map = new WeakHashMap<>();
        String k1 = new String("a");
        map.put(k1, 1);
        Spliterator<Map.Entry<String, Integer>> sp = map.entrySet().spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance(e -> map.put(new String("new"), 2));
        } catch (ConcurrentModificationException ex) {
            thrown = true;
        }
        check(thrown, "modification during tryAdvance must throw ConcurrentModificationException");
    }
}
