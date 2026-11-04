/*
 * @test
 * @summary Verify ConcurrentHashMap keySet().retainAll behavior through the public API
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testNullArgument();
        testRetainSubset();
        testRetainAllKeepsEverything();
        testRetainEmptyCollection();
        testEmptyMap();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static ConcurrentHashMap<String, Integer> newMap() {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        return m;
    }

    static void testNullArgument() {
        ConcurrentHashMap<String, Integer> m = newMap();
        boolean thrown = false;
        try {
            m.keySet().retainAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "NullPointerException expected for null argument");
        check(m.size() == 3, "map must be unchanged after NPE");
    }

    static void testRetainSubset() {
        ConcurrentHashMap<String, Integer> m = newMap();
        boolean modified = m.keySet().retainAll(Arrays.asList("a", "b"));
        check(modified, "retainAll should report modification");
        check(m.size() == 2, "size should be 2 after retaining a and b");
        check(m.containsKey("a") && m.containsKey("b"), "a and b should remain");
        check(!m.containsKey("c"), "c should have been removed");
    }

    static void testRetainAllKeepsEverything() {
        ConcurrentHashMap<String, Integer> m = newMap();
        boolean modified = m.keySet().retainAll(Arrays.asList("a", "b", "c", "x"));
        check(!modified, "retainAll should report no modification");
        check(m.size() == 3, "size should remain 3");
    }

    static void testRetainEmptyCollection() {
        ConcurrentHashMap<String, Integer> m = newMap();
        boolean modified = m.keySet().retainAll(new ArrayList<String>());
        check(modified, "retainAll with empty collection should modify non-empty map");
        check(m.isEmpty(), "map should be empty after retaining nothing");
    }

    static void testEmptyMap() {
        ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
        boolean modified = m.keySet().retainAll(Arrays.asList("a"));
        check(!modified, "retainAll on empty map should report no modification");
        check(m.isEmpty(), "map should remain empty");
    }
}
