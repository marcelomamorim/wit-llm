/*
 * @test
 * @summary Test for ConcurrentSkipListMap.put handling null keys
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testPutWithNullKey();
        testPutWithValidKey();
    }

    static void testPutWithNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.put(null, "value");
            System.out.println("FAIL: Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testPutWithValidKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "testKey";
        String value = "testValue";
        String prev = map.put(key, value);
        if (prev == null && map.get(key).equals(value)) {
            System.out.println("PASS: Successfully inserted and retrieved value");
        } else {
            System.out.println("FAIL: Insert or retrieval failed");
        }
    }
}
