/* @test    @summary Test getValue() method of IdentityHashMap for correct value retrieval and exception handling    @run main IdentityHashMapWitupTest */

import java.util.IdentityHashMap;

public class IdentityHashMapWitupTest {

    public static void main(String[] args) {
        testGetValueReturnsCorrectValue();
        testGetValueWithEmptyMapReturnsNull();
        testGetValueAfterRemovalReturnsNull();
    }

    private static void testGetValueReturnsCorrectValue() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        String key = "key";
        String value = "value";
        map.put(key, value);
        Object traversalTable = getTraversalTable(map);
        if (traversalTable == null) {
            throw new AssertionError("Traversal table should not be null after put.");
        }
        String retrievedValue = map.getValue();
        if (retrievedValue == null || !retrievedValue.equals(value)) {
            throw new AssertionError("Expected value: " + value + ", but got: " + retrievedValue);
        }
    }

    private static void testGetValueWithEmptyMapReturnsNull() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        Object traversalTable = getTraversalTable(map);
        if (traversalTable != null) {
            throw new AssertionError("Traversal table should be null for empty map.");
        }
        String value = map.getValue();
        if (value != null) {
            throw new AssertionError("Expected null for getValue() on empty map, but got: " + value);
        }
    }

    private static void testGetValueAfterRemovalReturnsNull() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        String key = "key";
        String value = "value";
        map.put(key, value);
        map.remove(key);
        Object traversalTable = getTraversalTable(map);
        if (traversalTable != null) {
            throw new AssertionError("Traversal table should be null after removal.");
        }
        String result = map.getValue();
        if (result != null) {
            throw new AssertionError("Expected null after removal, but got: " + result);
        }
    }

    private static Object getTraversalTable(IdentityHashMap<?, ?> map) {
        try {
            java.lang.reflect.Field tableField = IdentityHashMap.class.getDeclaredField("traversalTable");
            tableField.setAccessible(true);
            return tableField.get(map);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Reflection failed to access traversalTable", e);
        }
    }
}
