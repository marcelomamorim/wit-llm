/*
 * @test
 * @summary Test for ConcurrentSkipListMap.containsValue(Object)
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testContainsValueWithNull();
        testContainsValueWithExistingValue();
        testContainsValueWithNonExistingValue();
    }

    private static void testContainsValueWithNull() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        boolean exceptionThrown = false;
        try {
            map.containsValue(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when calling containsValue(null)");
        }
    }

    private static void testContainsValueWithExistingValue() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        boolean result = map.containsValue("one");
        if (!result) {
            throw new AssertionError("Map should contain value 'one'");
        }
    }

    private static void testContainsValueWithNonExistingValue() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        boolean result = map.containsValue("three");
        if (result) {
            throw new AssertionError("Map should not contain value 'three'");
        }
    }
}
