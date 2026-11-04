/*
 * @test
 * @summary Test TreeMap.replace(K key, V oldValue, V newValue) method
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.Objects;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testReplaceWithMatchingOldValue();
        testReplaceWithNonMatchingOldValue();
        testReplaceWithNullKeyAndValue();
        testReplaceOnEmptyMap();
    }

    private static void testReplaceWithMatchingOldValue() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("key1", "value1");
        boolean replaced = map.replace("key1", "value1", "value2");
        if (!replaced) {
            throw new RuntimeException("Expected replace to return true when oldValue matches");
        }
        String val = map.get("key1");
        if (!Objects.equals(val, "value2")) {
            throw new RuntimeException("Expected value to be replaced with newValue");
        }
    }

    private static void testReplaceWithNonMatchingOldValue() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("key1", "value1");
        boolean replaced = map.replace("key1", "wrongOldValue", "value2");
        if (replaced) {
            throw new RuntimeException("Expected replace to return false when oldValue does not match");
        }
        String val = map.get("key1");
        if (!Objects.equals(val, "value1")) {
            throw new RuntimeException("Expected value to remain unchanged when oldValue does not match");
        }
    }

    private static void testReplaceWithNullKeyAndValue() {
        TreeMap<String, String> map = new TreeMap<>();
        // TreeMap does not allow null keys by default (natural ordering), expect NullPointerException
        boolean npeThrown = false;
        try {
            map.replace(null, null, "newValue");
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when replacing with null key in natural ordering TreeMap");
        }

        // Using a TreeMap with a Comparator that allows null keys
        TreeMap<String, String> mapWithNulls = new TreeMap<>(
            (a, b) -> {
                if (a == null && b == null) return 0;
                if (a == null) return -1;
                if (b == null) return 1;
                return a.compareTo(b);
            }
        );
        mapWithNulls.put(null, null);
        boolean replaced = mapWithNulls.replace(null, null, "newValue");
        if (!replaced) {
            throw new RuntimeException("Expected replace to return true when oldValue matches null");
        }
        String val = mapWithNulls.get(null);
        if (!Objects.equals(val, "newValue")) {
            throw new RuntimeException("Expected value to be replaced with newValue for null key");
        }
    }

    private static void testReplaceOnEmptyMap() {
        TreeMap<String, String> map = new TreeMap<>();
        boolean replaced = map.replace("key", "oldValue", "newValue");
        if (replaced) {
            throw new RuntimeException("Expected replace to return false when map is empty");
        }
    }
}
