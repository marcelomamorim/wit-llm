/* @test    @summary Test replace method for ConcurrentSkipListMap    @run main ConcurrentSkipListMapWitupTest_Pded97f3685b7 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_Pded97f3685b7 {

    public static void main(String[] args) {
        testReplaceWithNullKey();
        testReplaceWithNullOldValue();
        testReplaceWithNullNewValue();
        testReplaceWithNonExistingKey();
        testReplaceWithMismatchedOldValue();
        testReplaceSuccessful();
    }

    static void testReplaceWithNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.replace(null, "old", "new");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testReplaceWithNullOldValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.replace("key", null, "new");
            throw new AssertionError("Expected NullPointerException for null oldValue");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testReplaceWithNullNewValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.replace("key", "old", null);
            throw new AssertionError("Expected NullPointerException for null newValue");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testReplaceWithNonExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean result = map.replace("nonexistent", "old", "new");
        if (result != false) {
            throw new AssertionError("Expected false when key does not exist");
        }
    }

    static void testReplaceWithMismatchedOldValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key", "value");
        boolean result = map.replace("key", "wrongOld", "new");
        if (result != false) {
            throw new AssertionError("Expected false when oldValue does not match");
        }
    }

    static void testReplaceSuccessful() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key", "old");
        boolean result = map.replace("key", "old", "new");
        if (result != true) {
            throw new AssertionError("Expected true for successful replace");
        }
        if (!"new".equals(map.get("key"))) {
            throw new AssertionError("Value not updated correctly");
        }
    }
}
