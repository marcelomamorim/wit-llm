/*
 * @test
 * @summary Test for containsKey method in ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testContainsKeyWithNullKey();
        testContainsKeyWithExistingKey();
        testContainsKeyWithNonExistingKey();
    }

    private static void testContainsKeyWithNullKey() {
        ConcurrentSkipListMap<Object, String> map = new ConcurrentSkipListMap<>();
        boolean exceptionThrown = false;
        try {
            map.containsKey(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            System.out.println("FAIL: Expected NullPointerException for null key");
        } else {
            System.out.println("PASS: NullPointerException thrown for null key");
        }
    }

    private static void testContainsKeyWithExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        boolean result = map.containsKey(1);
        if (result) {
            System.out.println("PASS: containsKey returns true for existing key");
        } else {
            System.out.println("FAIL: containsKey returns false for existing key");
        }
    }

    private static void testContainsKeyWithNonExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        boolean result = map.containsKey(2);
        if (!result) {
            System.out.println("PASS: containsKey returns false for non-existing key");
        } else {
            System.out.println("FAIL: containsKey returns true for non-existing key");
        }
    }
}
