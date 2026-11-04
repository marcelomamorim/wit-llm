/*
 * @test
 * @summary Test for ConcurrentHashMap.putIfAbsent() method, including exception path for null keys or values.
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testPutIfAbsentWithNullKey();
        testPutIfAbsentWithNullValue();
        testPutIfAbsentNormal();
    }

    private static void testPutIfAbsentWithNullKey() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.putIfAbsent(null, "value");
            System.out.println("FAIL: Expected NullPointerException for null key.");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null key as expected.");
        }
    }

    private static void testPutIfAbsentWithNullValue() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.putIfAbsent("key", null);
            System.out.println("FAIL: Expected NullPointerException for null value.");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null value as expected.");
        }
    }

    private static void testPutIfAbsentNormal() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        String result = map.putIfAbsent("key", "value");
        if (result == null && "value".equals(map.get("key"))) {
            System.out.println("PASS: putIfAbsent successfully added new key-value.");
        } else {
            System.out.println("FAIL: putIfAbsent did not behave as expected.");
        }
        // Test that it does not overwrite existing value
        String previous = map.putIfAbsent("key", "newValue");
        if ("value".equals(map.get("key")) && previous.equals("value")) {
            System.out.println("PASS: putIfAbsent did not overwrite existing value.");
        } else {
            System.out.println("FAIL: putIfAbsent overwrote existing value.");
        }
    }
}
