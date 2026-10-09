/* @test    @summary Test for ConcurrentSkipListMap.put handling nulls and exceptions    @run main ConcurrentSkipListMapWitupTest_Pa199c7aff357 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_Pa199c7aff357 {

    public static void main(String[] args) {
        testPutWithNullValue();
        testPutWithNullKey();
    }

    private static void testPutWithNullValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "testKey";
        String value = null;
        try {
            map.put(key, value);
            System.out.println("FAIL: Expected NullPointerException for null value.");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null value.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception type for null value: " + e);
        }
    }

    private static void testPutWithNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = null;
        String value = "value";
        try {
            map.put(key, value);
            System.out.println("FAIL: Expected NullPointerException for null key.");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null key.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception type for null key: " + e);
        }
    }
}
