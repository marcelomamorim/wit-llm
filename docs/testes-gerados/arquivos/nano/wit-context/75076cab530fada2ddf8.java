/*
 * @test
 * @summary Test replace method of ConcurrentSkipListMap for null key
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testReplaceWithNullKey();
        testReplaceWithValidKey();
    }

    private static void testReplaceWithNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.replace(null, "old", "new");
            System.out.println("FAIL: Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null key");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testReplaceWithValidKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("a", "old");
        boolean result = map.replace("a", "old", "new");
        if (result && "new".equals(map.get("a"))) {
            System.out.println("PASS: replace succeeded and value updated");
        } else {
            System.out.println("FAIL: replace did not work as expected");
        }
    }
}
