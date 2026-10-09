/*
 * @test
 * @summary Test the replace method of ConcurrentHashMap for null inputs and normal behavior
 * @run main ConcurrentHashMapWitupTest_P46cf9c7d609a
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest_P46cf9c7d609a {

    public static void main(String[] args) {
        testReplaceWithNullKey();
        testReplaceWithNullOldValue();
        testReplaceWithNullNewValue();
        testReplaceNormal();
    }

    static void testReplaceWithNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean threwException = false;
        try {
            map.replace(null, "old", "new");
        } catch (NullPointerException e) {
            threwException = true;
        }
        assert threwException : "Expected NullPointerException for null key";
    }

    static void testReplaceWithNullOldValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean threwException = false;
        try {
            map.replace("key", null, "new");
        } catch (NullPointerException e) {
            threwException = true;
        }
        assert threwException : "Expected NullPointerException for null oldValue";
    }

    static void testReplaceWithNullNewValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean threwException = false;
        try {
            map.replace("key", "old", null);
        } catch (NullPointerException e) {
            threwException = true;
        }
        assert threwException : "Expected NullPointerException for null newValue";
    }

    static void testReplaceNormal() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "old");
        boolean result = map.replace("key", "old", "new");
        assert result : "Replace should succeed when key exists with expected oldValue";
        assert "new" .equals(map.get("key")) : "Value should be updated to new";
    }
}
