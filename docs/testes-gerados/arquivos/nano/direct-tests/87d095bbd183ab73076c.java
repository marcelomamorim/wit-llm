/*
 * @test
 * @summary Test containsValue method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testContainsValueWithExistingValue();
        testContainsValueWithNonExistingValue();
        testContainsValueWithNullThrows();
    }

    static void testContainsValueWithExistingValue() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        boolean result = map.containsValue("two");
        if (!result) {
            throw new AssertionError("Expected map to contain value 'two'");
        }
    }

    static void testContainsValueWithNonExistingValue() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        boolean result = map.containsValue("four");
        if (result) {
            throw new AssertionError("Did not expect map to contain value 'four'");
        }
    }

    static void testContainsValueWithNullThrows() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        boolean threw = false;
        try {
            map.containsValue(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException when calling containsValue(null)");
        }
    }
}
