/*
 * @test
 * @summary Test remove method of ConcurrentSkipListMap for null key
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testRemoveWithNullKey();
        testRemoveWithValidKey();
    }

    private static void testRemoveWithNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.remove(null);
            System.out.println("remove(null) did not throw NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("remove(null) threw NullPointerException as expected");
        } catch (Exception e) {
            System.out.println("remove(null) threw unexpected exception: " + e);
        }
    }

    private static void testRemoveWithValidKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key1", "value1");
        String removed = map.remove("key1");
        if ("value1".equals(removed) && !map.containsKey("key1")) {
            System.out.println("remove(" + "key1" + ") succeeded and key was removed");
        } else {
            System.out.println("remove(" + "key1" + ") failed or key still present");
        }
    }
}
