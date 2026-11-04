/*
 * @test
 * @summary Test for ConcurrentSkipListMap.putIfAbsent method, focusing on null key handling.
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testPutIfAbsentWithNullKey();
        testPutIfAbsentWithValidKey();
    }

    private static void testPutIfAbsentWithNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.putIfAbsent(null, "value");
            System.out.println("FAIL: Expected NullPointerException for null key.");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null key.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception type: " + e);
        }
    }

    private static void testPutIfAbsentWithValidKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String result = map.putIfAbsent("key1", "value1");
        if (result == null && "value1".equals(map.get("key1"))) {
            System.out.println("PASS: putIfAbsent with valid key succeeded.");
        } else {
            System.out.println("FAIL: putIfAbsent with valid key failed.");
        }
        // Test that it does not overwrite existing value
        String secondResult = map.putIfAbsent("key1", "value2");
        if (secondResult != null && secondResult.equals("value1") && "value1".equals(map.get("key1"))) {
            System.out.println("PASS: putIfAbsent did not overwrite existing value.");
        } else {
            System.out.println("FAIL: putIfAbsent overwrote existing value.");
        }
    }
}
