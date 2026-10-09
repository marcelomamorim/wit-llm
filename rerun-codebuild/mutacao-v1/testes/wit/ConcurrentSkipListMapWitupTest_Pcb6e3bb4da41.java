/* @test    @summary Test putIfAbsent for ConcurrentSkipListMap with null value and null key scenarios    @run main ConcurrentSkipListMapWitupTest_Pcb6e3bb4da41 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_Pcb6e3bb4da41 {

    public static void main(String[] args) {
        testPutIfAbsentNullValue();
        testPutIfAbsentNullKey();
    }

    private static void testPutIfAbsentNullValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "testKey";
        String value = null;
        try {
            map.putIfAbsent(key, value);
            throw new AssertionError("Expected NullPointerException for null value");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testPutIfAbsentNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = null;
        String value = "value";
        try {
            map.putIfAbsent(key, value);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
