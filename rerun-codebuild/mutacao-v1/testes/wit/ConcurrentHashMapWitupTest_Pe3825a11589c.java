/* @test    @summary Test replace method for ConcurrentHashMap    @run main ConcurrentHashMapWitupTest_Pe3825a11589c */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest_Pe3825a11589c {

    public static void main(String[] args) {
        testReplaceWithNullKey();
        testReplaceWithNullOldValue();
        testReplaceWithNullNewValue();
        testReplaceNormal();
    }

    private static void testReplaceWithNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.replace(null, "old", "new");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testReplaceWithNullOldValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.replace("key", null, "new");
            throw new AssertionError("Expected NullPointerException for null oldValue");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testReplaceWithNullNewValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.replace("key", "old", null);
            throw new AssertionError("Expected NullPointerException for null newValue");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testReplaceNormal() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "oldValue");
        boolean result = map.replace("key", "oldValue", "newValue");
        if (!result) {
            throw new AssertionError("Replace should succeed with correct oldValue");
        }
        if (!"newValue".equals(map.get("key"))) {
            throw new AssertionError("Value not replaced correctly");
        }
    }
}
