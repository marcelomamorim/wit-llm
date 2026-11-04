/*
 * @test
 * @summary Test containsKey method of ConcurrentSkipListMap for various keys
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
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean result = map.containsKey(null);
        System.out.println("containsKey(null): " + result);
    }

    static void testContainsKeyWithExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        boolean result = map.containsKey(1);
        System.out.println("containsKey(1): " + result);
    }

    static void testContainsKeyWithNonExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(2, "two");
        boolean result = map.containsKey(3);
        System.out.println("containsKey(3): " + result);
    }
}
