/*
 * @test    @summary Test get() method of ConcurrentSkipListMap for various keys
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testGetWithNullKey();
        testGetWithKeyInBounds();
        testGetWithKeyOutOfBounds();
    }

    static void testGetWithNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean exceptionThrown = false;
        try {
            map.get(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null key");
        }
    }

    static void testGetWithKeyInBounds() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("a", "alpha");
        map.put("b", "beta");
        map.put("c", "gamma");
        String result = map.get("b");
        if (!"beta".equals(result)) {
            throw new AssertionError("Expected 'beta', got: " + result);
        }
    }

    static void testGetWithKeyOutOfBounds() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("a", "alpha");
        map.put("b", "beta");
        map.put("c", "gamma");
        String resultLower = map.get("-a");
        String resultHigher = map.get("z");
        if (resultLower != null) {
            throw new AssertionError("Expected null for key '-a', got: " + resultLower);
        }
        if (resultHigher != null) {
            throw new AssertionError("Expected null for key 'z', got: " + resultHigher);
        }
    }
}
