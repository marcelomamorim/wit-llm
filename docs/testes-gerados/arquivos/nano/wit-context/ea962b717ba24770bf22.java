/*
 * @test
 * @summary Test for ConcurrentSkipListMap.replace(K key, V value) method
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testReplaceWithNullKey();
        testReplaceWithValidKey();
    }

    static void testReplaceWithNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.replace(null, "value");
            System.out.println("FAIL: Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            System.out.println("PASS: NullPointerException thrown as expected for null key");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testReplaceWithValidKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("a", "initial");
        String oldValue = map.replace("a", "new");
        if ("initial".equals(oldValue) && "new".equals(map.get("a"))) {
            System.out.println("PASS: replace with valid key works as expected");
        } else {
            System.out.println("FAIL: replace with valid key did not behave as expected");
        }
    }
}
