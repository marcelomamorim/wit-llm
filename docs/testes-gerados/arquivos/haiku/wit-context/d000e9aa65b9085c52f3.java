/*
 * @test
 * @summary Checks LinkedHashMap key-set forEach null action, insertion order and concurrent modification detection
 * @run main LinkedHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedHashMap;
import java.util.List;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testNullActionOnEmptyMapThrowsNPE();
        testInsertionOrderIteration();
        testConcurrentModificationDetected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNPE() {
        LinkedHashMap<String, Integer> m = new LinkedHashMap<>();
        m.put("a", 1);
        boolean thrown = false;
        try {
            m.keySet().forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testNullActionOnEmptyMapThrowsNPE() {
        LinkedHashMap<String, Integer> m = new LinkedHashMap<>();
        boolean thrown = false;
        try {
            m.keySet().forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action on empty map");
    }

    static void testInsertionOrderIteration() {
        LinkedHashMap<String, Integer> m = new LinkedHashMap<>();
        m.put("c", 3);
        m.put("a", 1);
        m.put("b", 2);
        final List<String> seen = new ArrayList<>();
        m.keySet().forEach(k -> seen.add(k));
        check(seen.toString().equals("[c, a, b]"),
              "unexpected iteration order: " + seen);
    }

    static void testConcurrentModificationDetected() {
        LinkedHashMap<String, Integer> m = new LinkedHashMap<>();
        m.put("x", 1);
        m.put("y", 2);
        final boolean[] done = {false};
        boolean thrown = false;
        try {
            m.keySet().forEach(k -> {
                if (!done[0]) {
                    done[0] = true;
                    m.put("z", 0);
                }
            });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException after structural modification");
    }
}
