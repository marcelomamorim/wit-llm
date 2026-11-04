/*
 * @test
 * @summary Test for ConcurrentSkipListMap.put method, focusing on exception paths.
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testPutWithNullValue();
        testPutWithNullKeyAndNonNullValue();
    }

    private static void testPutWithNullValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.put("key", null);
            System.out.println("Failed: Expected NullPointerException for null value");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null value");
        }
    }

    private static void testPutWithNullKeyAndNonNullValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.put(null, "value");
            System.out.println("Failed: Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null key");
        }
    }
}
