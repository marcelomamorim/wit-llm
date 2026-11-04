/*
 * @test
 * @summary Test constructor WeakHashMap(Map<? extends K, ? extends V>) for basic behavior and exception safety
 * @run main WeakHashMapWitupTest
 */

import java.util.Map;
import java.util.HashMap;
import java.util.WeakHashMap;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidMap();
        testConstructorWithEmptyMap();
        testConstructorWithNullMap();
    }

    private static void testConstructorWithValidMap() {
        Map<String, String> source = new HashMap<>();
        source.put("key1", "value1");
        source.put("key2", "value2");

        WeakHashMap<String, String> whm = new WeakHashMap<>(source);

        // Check that all entries are copied
        if (!"value1".equals(whm.get("key1"))) {
            throw new AssertionError("Expected value1 for key1");
        }
        if (!"value2".equals(whm.get("key2"))) {
            throw new AssertionError("Expected value2 for key2");
        }

        // Check size matches
        if (whm.size() != source.size()) {
            throw new AssertionError("Expected size " + source.size() + " but got " + whm.size());
        }
    }

    private static void testConstructorWithEmptyMap() {
        Map<Object, Object> empty = new HashMap<>();
        WeakHashMap<Object, Object> whm = new WeakHashMap<>(empty);
        if (whm.size() != 0) {
            throw new AssertionError("Expected empty WeakHashMap size 0");
        }
    }

    private static void testConstructorWithNullMap() {
        try {
            new WeakHashMap<>(null);
            throw new AssertionError("Expected NullPointerException for null map argument");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
