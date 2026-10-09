/*
 * @test
 * @summary Test putIfAbsent for ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest_P281c30380e98
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_P281c30380e98 {

    public static void main(String[] args) {
        testPutIfAbsentWithNullValue();
        testPutIfAbsentWithNullKey();
    }

    private static void testPutIfAbsentWithNullValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "key1";
        String value = null;
        try {
            map.putIfAbsent(key, value);
            System.out.println("Failed: Expected NullPointerException for null value.");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null value.");
        }
    }

    private static void testPutIfAbsentWithNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = null;
        String value = "value1";
        try {
            map.putIfAbsent(key, value); 
            System.out.println("Failed: Expected NullPointerException for null key.");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null key.");
        }
    }
}
