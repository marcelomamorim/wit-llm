/*
 * @test    @summary Test containsKey method of ConcurrentSkipListMap for null and valid keys
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testContainsKeyWithNull();
        testContainsKeyWithExistingKey();
        testContainsKeyWithNonExistingKey();
    }

    static void testContainsKeyWithNull() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        boolean threw = false;
        try {
            map.containsKey(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException for null key");
        }
    }

    static void testContainsKeyWithExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        boolean result = map.containsKey(1);
        if (!result) {
            throw new AssertionError("Map should contain key 1");
        }
    }

    static void testContainsKeyWithNonExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(2, "two");
        boolean result = map.containsKey(3);
        if (result) {
            throw new AssertionError("Map should not contain key 3");
        }
    }
}
