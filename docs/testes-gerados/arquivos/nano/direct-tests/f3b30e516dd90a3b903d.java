/*
 * @test
 * @summary Test get method of ConcurrentSkipListMap for various keys.
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testGetExistingKey();
        testGetNonExistingKey();
        testGetNullKey();
    }

    static void testGetExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("a", "alpha");
        String result = map.get("a");
        if (!"alpha".equals(result)) {
            throw new AssertionError("Expected 'alpha', got: " + result);
        }
    }

    static void testGetNonExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("b", "beta");
        String result = map.get("c");
        if (result != null) {
            throw new AssertionError("Expected null for missing key, got: " + result);
        }
    }

    static void testGetNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.get(null);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
