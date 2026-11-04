/*
 * @test    @summary Test retainAll method of ConcurrentHashMap
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testRetainAllWithEmptyMap();
        testRetainAllWithMatchingElements();
        testRetainAllWithNonMatchingElements();
        testRetainAllWithNullCollection();
    }

    static void testRetainAllWithEmptyMap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        Collection<String> c = java.util.Arrays.asList("a", "b");
        boolean result = map.retainAll(c);
        assert result == false : "Expected false when map is empty";
        assert map.isEmpty() : "Map should remain empty";
    }

    static void testRetainAllWithMatchingElements() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        Collection<String> c = java.util.Arrays.asList("a", "b", "c");
        boolean result = map.retainAll(c);
        assert result == true : "Expected true when map is modified";
        assert map.size() == 2 : "Map should contain 2 entries";
        assert map.containsKey("a") : "Map should contain key 'a'";
        assert map.containsKey("b") : "Map should contain key 'b'";
    }

    static void testRetainAllWithNonMatchingElements() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("x", "10");
        map.put("y", "20");
        Collection<String> c = java.util.Arrays.asList("a", "b");
        boolean result = map.retainAll(c);
        assert result == true : "Expected true when entries are removed";
        assert map.isEmpty() : "Map should be empty after retainAll";
    }

    static void testRetainAllWithNullCollection() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.retainAll(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
