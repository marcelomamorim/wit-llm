/*
 * @test
 * @summary Test WeakHashMap.toArray(T[] a) method behavior
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.Arrays;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        testToArrayWithEmptyMap();
        testToArrayWithNonEmptyMap();
        testToArrayWithSmallerArray();
        testToArrayWithLargerArray();
    }

    private static void testToArrayWithEmptyMap() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        String[] arr = new String[0];
        String[] result = map.toArray(arr);
        if (result.length != 0) {
            throw new AssertionError("Expected empty array from empty map");
        }
    }

    private static void testToArrayWithNonEmptyMap() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        Object[] result = map.toArray(new Object[0]);
        if (result.length != 2) {
            throw new AssertionError("Expected array length 2 for map with 2 entries");
        }
        // Check that all entries are present
        boolean foundKey1 = false;
        boolean foundKey2 = false;
        for (Object o : result) {
            if (!(o instanceof java.util.Map.Entry)) {
                throw new AssertionError("Array element is not a Map.Entry");
            }
            java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>) o;
            if ("key1".equals(e.getKey()) && "value1".equals(e.getValue())) {
                foundKey1 = true;
            } else if ("key2".equals(e.getKey()) && "value2".equals(e.getValue())) {
                foundKey2 = true;
            }
        }
        if (!foundKey1 || !foundKey2) {
            throw new AssertionError("Expected entries not found in toArray result");
        }
    }

    private static void testToArrayWithSmallerArray() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        java.util.Map.Entry[] smallArray = new java.util.Map.Entry[1];
        java.util.Map.Entry[] result = map.toArray(smallArray);
        if (result.length != 3) {
            throw new AssertionError("Expected new array of length 3 when input array is smaller");
        }
    }

    private static void testToArrayWithLargerArray() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("x", "10");
        java.util.Map.Entry[] largeArray = new java.util.Map.Entry[5];
        java.util.Map.Entry[] result = map.toArray(largeArray);
        if (result != largeArray) {
            throw new AssertionError("Expected toArray to return the same array if it is large enough");
        }
        if (result[0] == null) {
            throw new AssertionError("Expected first element to be non-null entry");
        }
        if (result[1] != null) {
            throw new AssertionError("Expected element after last entry to be null");
        }
    }
}
