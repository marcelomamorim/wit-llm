/*
 * @test
 * @summary Test deterministic behavior of WeakHashMap.toArray(T[] a) method
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        testToArrayWithEmptyMap();
        testToArrayWithNonEmptyMap();
    }

    private static void testToArrayWithEmptyMap() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        // toArray with empty map should return empty array
        Map.Entry<String, String>[] arr = entrySet.toArray(new Map.Entry[0]);
        if (arr.length != 0) {
            throw new AssertionError("Expected empty array for empty map, got length " + arr.length);
        }
    }

    private static void testToArrayWithNonEmptyMap() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        Set<Map.Entry<String, String>> entrySet = map.entrySet();

        // toArray with array of exact size
        Map.Entry<String, String>[] arrExact = entrySet.toArray(new Map.Entry[entrySet.size()]);
        if (arrExact.length != entrySet.size()) {
            throw new AssertionError("Expected array length equal to entrySet size");
        }

        // toArray with array smaller than size, should allocate new array
        Map.Entry<String, String>[] arrSmall = entrySet.toArray(new Map.Entry[0]);
        if (arrSmall.length != entrySet.size()) {
            throw new AssertionError("Expected array length equal to entrySet size when input array is smaller");
        }

        // toArray with array larger than size, extra element should be null
        Map.Entry<String, String>[] arrLarge = entrySet.toArray(new Map.Entry[entrySet.size() + 2]);
        if (arrLarge.length != entrySet.size() + 2) {
            throw new AssertionError("Expected array length equal to input array length when larger");
        }
        if (arrLarge[entrySet.size()] != null) {
            throw new AssertionError("Expected null element after last entry in larger array");
        }

        // Verify that all entries in array are contained in the map
        for (int i = 0; i < entrySet.size(); i++) {
            Map.Entry<String, String> e = arrExact[i];
            if (!map.containsKey(e.getKey()) || !map.get(e.getKey()).equals(e.getValue())) {
                throw new AssertionError("Array element not matching map entry");
            }
        }
    }
}
