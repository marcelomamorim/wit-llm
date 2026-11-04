/*
 * @test
 * @summary Witup-derived tests for HashMap key set forEach null action and concurrent modification
 * @run main HashMapWitupTest
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.ConcurrentModificationException;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testNullActionOnEmptyMapThrowsNpe();
        testIterationVisitsAllKeys();
        testModificationDuringIterationThrowsCme();
    }

    static void testNullActionThrowsNpe() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        Set<String> keys = m.keySet();
        try {
            keys.forEach(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null action");
    }

    static void testNullActionOnEmptyMapThrowsNpe() {
        HashMap<String, Integer> m = new HashMap<>();
        Set<String> keys = m.keySet();
        try {
            keys.forEach(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null action on empty map");
    }

    static void testIterationVisitsAllKeys() {
        HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        final List<String> seen = new ArrayList<>();
        m.keySet().forEach(k -> seen.add(k));
        if (seen.size() != 3) {
            throw new RuntimeException("expected 3 keys visited, got " + seen.size());
        }
        if (!seen.contains("a") || !seen.contains("b") || !seen.contains("c")) {
            throw new RuntimeException("not all keys visited: " + seen);
        }
    }

    static void testModificationDuringIterationThrowsCme() {
        final HashMap<String, Integer> m = new HashMap<>();
        m.put("a", 1);
        final int[] calls = new int[1];
        try {
            m.keySet().forEach(k -> {
                if (calls[0]++ == 0) {
                    m.put("new", 2);
                }
            });
        } catch (ConcurrentModificationException e) {
            return;
        }
        throw new RuntimeException("expected ConcurrentModificationException");
    }
}
