/*
 * @test
 * @summary Test for containsValue method in ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest_P7906830e685f
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_P7906830e685f {

    public static void main(String[] args) {
        testContainsValueWithNull();
        testContainsValueWithExistingObject();
        testContainsValueWithNonExistingObject();
    }

    private static void testContainsValueWithNull() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        boolean threwException = false;
        try {
            map.containsValue(null);
        } catch (NullPointerException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected NullPointerException when calling containsValue(null)");
        }
    }

    private static void testContainsValueWithExistingObject() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        String value = "test";
        map.put(1, value);
        boolean result = map.containsValue(value);
        if (!result) {
            throw new AssertionError("Map should contain the value after put");
        }
    }

    private static void testContainsValueWithNonExistingObject() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        boolean result = map.containsValue("not present");
        if (result) {
            throw new AssertionError("Map should not contain the non-existing value");
        }
    }
}
