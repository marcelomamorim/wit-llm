/*
 * @test
 * @summary Test for ConcurrentSkipListMap.get method, including null key handling
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testGetWithNullKey();
        testGetWithExistingKey();
        testGetWithNonExistingKey();
    }

    private static void testGetWithNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.get(null);
            System.out.println("testGetWithNullKey: FAILED - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testGetWithNullKey: PASSED");
        } catch (Exception e) {
            System.out.println("testGetWithNullKey: FAILED - Unexpected exception: " + e);
        }
    }

    private static void testGetWithExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        String result = map.get("key1");
        if ("value1".equals(result)) {
            System.out.println("testGetWithExistingKey: PASSED");
        } else {
            System.out.println("testGetWithExistingKey: FAILED - Unexpected result: " + result);
        }
    }

    private static void testGetWithNonExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        String result = map.get("nonexistent");
        if (result == null) {
            System.out.println("testGetWithNonExistingKey: PASSED");
        } else {
            System.out.println("testGetWithNonExistingKey: FAILED - Unexpected result: " + result);
        }
    }
}
