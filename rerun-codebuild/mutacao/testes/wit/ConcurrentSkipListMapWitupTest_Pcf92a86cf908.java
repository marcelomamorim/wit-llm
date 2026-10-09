/*
 * @test
 * @summary Tests for ConcurrentSkipListMap.putIfAbsent method
 * @run main ConcurrentSkipListMapWitupTest_Pcf92a86cf908
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_Pcf92a86cf908 {

    public static void main(String[] args) {
        testPutIfAbsentWithNullValue();
        testPutIfAbsentWithNullKey();
    }

    private static void testPutIfAbsentWithNullValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "testKey";
        String value = null;
        try {
            map.putIfAbsent(key, value);
            // Expecting NullPointerException, so if no exception, fail
            throw new AssertionError("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
            // Test passes
        }
    }

    private static void testPutIfAbsentWithNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = null;
        String value = "testValue";
        try {
            map.putIfAbsent(key, value);
            // Expecting NullPointerException, so if no exception, fail
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // Test passes
        }
    }
}
