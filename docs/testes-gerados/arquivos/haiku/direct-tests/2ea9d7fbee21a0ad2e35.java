/*
 * @test
 * @summary Checks ConcurrentHashMap key-view forEach visits every key, handles empty maps and removals, and rejects a null action with NullPointerException
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testForEachVisitsAllKeys();
        testForEachEmptyMap();
        testForEachNullActionThrowsNPE();
        testForEachAfterRemove();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testForEachVisitsAllKeys() {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        final List<String> visits = new ArrayList<>();
        m.keySet().forEach(k -> visits.add(k));
        check(visits.size() == 3, "expected 3 visits but was " + visits.size());
        Set<String> seen = new HashSet<>(visits);
        check(seen.equals(m.keySet()), "visited keys differ from map keys");
    }

    static void testForEachEmptyMap() {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        final int[] count = new int[1];
        m.keySet().forEach(k -> count[0]++);
        check(count[0] == 0, "expected no visits on empty map but was " + count[0]);
    }

    static void testForEachNullActionThrowsNPE() {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        m.put("x", 1);
        Consumer<String> nullAction = null;
        boolean thrown = false;
        try {
            m.keySet().forEach(nullAction);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testForEachAfterRemove() {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        m.put("p", 1);
        m.put("q", 2);
        m.remove("p");
        final List<String> visits = new ArrayList<>();
        m.keySet().forEach(k -> visits.add(k));
        check(visits.size() == 1, "expected 1 visit after remove but was " + visits.size());
        check(visits.contains("q"), "expected key q to be visited");
        check(!visits.contains("p"), "removed key p must not be visited");
    }
}
