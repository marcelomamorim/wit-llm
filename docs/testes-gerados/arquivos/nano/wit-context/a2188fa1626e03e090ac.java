/*
 * @test
 * @summary Test replace method of ConcurrentSkipListMap for null inputs
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testReplaceWithNullKey();
        testReplaceWithNullOldValue();
        testReplaceWithNullNewValue();
    }

    private static void testReplaceWithNullKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.replace(null, "old", "new");
            System.out.println("FAIL: Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null key");
        }
    }

    private static void testReplaceWithNullOldValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.replace("key", null, "new");
            System.out.println("FAIL: Expected NullPointerException for null oldValue");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null oldValue");
        }
    }

    private static void testReplaceWithNullNewValue() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.replace("key", "old", null);
            System.out.println("FAIL: Expected NullPointerException for null newValue");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null newValue");
        }
    }
}
