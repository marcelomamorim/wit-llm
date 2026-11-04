/*
 * @test    @summary Test replace method of ConcurrentHashMap for null and valid inputs
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testReplaceWithNullKey();
        testReplaceWithNullValue();
        testReplaceWithValidInputs();
    }

    static void testReplaceWithNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.replace(null, "value");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testReplaceWithNullValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "initial");
        try {
            map.replace("key", null);
            throw new AssertionError("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testReplaceWithValidInputs() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key", "initial");
        String previous = map.replace("key", "newValue");
        if (previous == null || !previous.equals("initial")) {
            throw new AssertionError("Unexpected previous value: " + previous);
        }
        String current = map.get("key");
        if (!"newValue".equals(current)) {
            throw new AssertionError("Replace did not update value, current: " + current);
        }
    }
}
