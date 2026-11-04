/*
 * @test
 * @summary Behavioral tests for TreeMap entry spliterator forEachRemaining
 * @run main TreeMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testVisitsAllInOrder();
        testVisitsRemainingAfterTryAdvance();
        testEmptyMapDoesNothing();
        testSpliteratorExhaustedAfterForEachRemaining();
        testConcurrentModificationDetected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static TreeMap<Integer, String> sampleMap() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(3, "c");
        map.put(1, "a");
        map.put(2, "b");
        return map;
    }

    static void testNullActionThrowsNPE() {
        TreeMap<Integer, String> map = sampleMap();
        Spliterator<Map.Entry<Integer, String>> s = map.entrySet().spliterator();
        boolean thrown = false;
        try {
            s.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testVisitsAllInOrder() {
        TreeMap<Integer, String> map = sampleMap();
        Spliterator<Map.Entry<Integer, String>> s = map.entrySet().spliterator();
        List<String> seen = new ArrayList<>();
        s.forEachRemaining(e -> seen.add(e.getKey() + "=" + e.getValue()));
        check(seen.size() == 3, "expected 3 entries but got " + seen);
        check(seen.get(0).equals("1=a"), "unexpected first entry " + seen);
        check(seen.get(1).equals("2=b"), "unexpected second entry " + seen);
        check(seen.get(2).equals("3=c"), "unexpected third entry " + seen);
    }

    static void testVisitsRemainingAfterTryAdvance() {
        TreeMap<Integer, String> map = sampleMap();
        Spliterator<Map.Entry<Integer, String>> s = map.entrySet().spliterator();
        List<Integer> consumed = new ArrayList<>();
        boolean advanced = s.tryAdvance(e -> consumed.add(e.getKey()));
        check(advanced, "tryAdvance should return true on non-empty map");
        check(consumed.size() == 1 && consumed.get(0) == 1, "first element should be key 1 but got " + consumed);
        List<Integer> rest = new ArrayList<>();
        s.forEachRemaining(e -> rest.add(e.getKey()));
        check(rest.size() == 2, "expected 2 remaining entries but got " + rest);
        check(rest.get(0) == 2 && rest.get(1) == 3, "unexpected remaining keys " + rest);
    }

    static void testEmptyMapDoesNothing() {
        TreeMap<Integer, String> map = new TreeMap<>();
        Spliterator<Map.Entry<Integer, String>> s = map.entrySet().spliterator();
        int[] count = new int[1];
        s.forEachRemaining(e -> count[0]++);
        check(count[0] == 0, "action should not be invoked for empty map");
    }

    static void testSpliteratorExhaustedAfterForEachRemaining() {
        TreeMap<Integer, String> map = sampleMap();
        Spliterator<Map.Entry<Integer, String>> s = map.entrySet().spliterator();
        s.forEachRemaining(e -> { });
        boolean advanced = s.tryAdvance(e -> { });
        check(!advanced, "spliterator should be exhausted after forEachRemaining");
    }

    static void testConcurrentModificationDetected() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "a");
        map.put(2, "b");
        Spliterator<Map.Entry<Integer, String>> s = map.entrySet().spliterator();
        boolean[] done = new boolean[1];
        boolean thrown = false;
        try {
            s.forEachRemaining(e -> {
                if (!done[0]) {
                    done[0] = true;
                    map.put(10, "x");
                }
            });
        } catch (ConcurrentModificationException ex) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException after structural modification");
    }
}
