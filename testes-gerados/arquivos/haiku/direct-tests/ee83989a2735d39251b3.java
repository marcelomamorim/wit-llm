/*
 * @test
 * @summary Tests ConcurrentHashMap KeySpliterator.forEachRemaining via keySet().spliterator()
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.HashSet;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testEmptyMapVisitsNothing();
        testVisitsAllKeys();
        testVisitsRemainingAfterTryAdvance();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        Spliterator<String> s = map.keySet().spliterator();
        boolean thrown = false;
        try {
            s.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEachRemaining(null) must throw NullPointerException");

        Set<String> visited = new HashSet<>();
        s.forEachRemaining(visited::add);
        check(visited.equals(Set.of("a")), "Key must still be visited after failed null call");
    }

    static void testEmptyMapVisitsNothing() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        int[] count = new int[1];
        map.keySet().spliterator().forEachRemaining(k -> count[0]++);
        check(count[0] == 0, "Empty map must visit no keys, got " + count[0]);
    }

    static void testVisitsAllKeys() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("x", 1);
        map.put("y", 2);
        map.put("z", 3);
        Set<String> visited = new HashSet<>();
        int[] count = new int[1];
        map.keySet().spliterator().forEachRemaining(k -> {
            visited.add(k);
            count[0]++;
        });
        check(count[0] == 3, "Expected 3 visits, got " + count[0]);
        check(visited.equals(Set.of("x", "y", "z")), "Visited keys mismatch: " + visited);
    }

    static void testVisitsRemainingAfterTryAdvance() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("p", 1);
        map.put("q", 2);
        map.put("r", 3);
        Spliterator<String> s = map.keySet().spliterator();
        Set<String> visited = new HashSet<>();
        int[] count = new int[1];
        check(s.tryAdvance(k -> { visited.add(k); count[0]++; }), "tryAdvance must consume one key");
        s.forEachRemaining(k -> { visited.add(k); count[0]++; });
        check(count[0] == 3, "Total visits must be 3, got " + count[0]);
        check(visited.equals(Set.of("p", "q", "r")), "Visited keys mismatch: " + visited);
    }
}
