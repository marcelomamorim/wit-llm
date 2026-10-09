/*
 * @test
 * @summary Test for ConcurrentHashMap.replace method, covering null argument cases.
 * @run main ConcurrentHashMapWitupTest_P8f32dd487d1c
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest_P8f32dd487d1c {

    public static void main(String[] args) {
        testReplaceWithNullKey();
        testReplaceWithNullOldValue();
        testReplaceWithNullNewValue();
    }

    private static void testReplaceWithNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.replace(null, "old", "new");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testReplaceWithNullOldValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.replace("key", null, "new");
            throw new AssertionError("Expected NullPointerException for null oldValue");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testReplaceWithNullNewValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.replace("key", "old", null);
            throw new AssertionError("Expected NullPointerException for null newValue");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
