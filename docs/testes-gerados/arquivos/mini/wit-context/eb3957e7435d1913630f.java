/*
 * @test
 * @summary Test ConcurrentHashMap retainAll(Collection<?>) method for NPE and behavior
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testRetainAllNullCollection();
        testRetainAllRemovesNonContained();
        testRetainAllNoModification();
    }

    private static void testRetainAllNullCollection() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.keySet().retainAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRetainAllRemovesNonContained() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");

        Collection<String> retain = new ArrayList<>();
        retain.add("a");
        retain.add("c");

        boolean modified = map.keySet().retainAll(retain);
        if (!modified) {
            throw new RuntimeException("Expected retainAll to modify the map");
        }

        if (map.size() != 2 || !map.containsKey("a") || !map.containsKey("c") || map.containsKey("b")) {
            throw new RuntimeException("retainAll did not remove keys correctly");
        }
    }

    private static void testRetainAllNoModification() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("x", "10");
        map.put("y", "20");

        Collection<String> retain = Arrays.asList("x", "y");

        boolean modified = map.keySet().retainAll(retain);
        if (modified) {
            throw new RuntimeException("Expected retainAll to not modify the map");
        }

        if (map.size() != 2 || !map.containsKey("x") || !map.containsKey("y")) {
            throw new RuntimeException("retainAll modified map unexpectedly");
        }
    }
}
