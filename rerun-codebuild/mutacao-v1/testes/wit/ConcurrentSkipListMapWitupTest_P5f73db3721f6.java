/* @test    @summary Test for ConcurrentSkipListMap.put handling nulls and exceptions    @run main ConcurrentSkipListMapWitupTest_P5f73db3721f6 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_P5f73db3721f6 {

    public static void main(String[] args) {
        testPutWithNullValue();
        testPutWithNullKeyAndNonNullValue();
    }

    private static void testPutWithNullValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean exceptionThrown = false;
        try {
            map.put("key1", null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when putting null value");
        }
    }

    private static void testPutWithNullKeyAndNonNullValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean exceptionThrown = false;
        try {
            map.put(null, "value1");
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when putting null key");
        }
    }
}
