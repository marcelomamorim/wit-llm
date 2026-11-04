/* @test    @summary Test the get method of TreeMap for normal and null keys    @run main TreeMapWitupTest */

import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testGetWithExistingKey();
        testGetWithNullKey();
        testGetWithNonExistingKey();
    }

    private static void testGetWithExistingKey() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("a", "alpha");
        String result = map.get("a");
        if (result == null || !result.equals("alpha")) {
            throw new AssertionError("Expected 'alpha', got: " + result);
        }
    }

    private static void testGetWithNullKey() {
        TreeMap<String, String> map = new TreeMap<>();
        try {
            map.get(null);
        } catch (NullPointerException e) {
            // Expected exception
            return;
        }
        // If no exception, test passes but result should be null
        String result = map.get(null);
        if (result != null) {
            throw new AssertionError("Expected null for null key, got: " + result);
        }
    }

    private static void testGetWithNonExistingKey() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("a", "alpha");
        String result = map.get("b");
        if (result != null) {
            throw new AssertionError("Expected null for non-existing key, got: " + result);
        }
    }
}
