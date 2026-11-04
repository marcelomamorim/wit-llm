/*
 * @test
 * @summary Test replace method of ConcurrentSkipListMap for correctness and regressions
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testReplaceExistingKey();
        testReplaceNonExistingKey();
    }

    private static void testReplaceExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "existing";
        String initialValue = "initial";
        String newValue = "updated";
        map.put(key, initialValue);
        String result = map.replace(key, newValue);
        if (!result.equals(initialValue)) {
            throw new AssertionError("Expected previous value to be '" + initialValue + "', but got '" + result + "'");
        }
        String currentValue = map.get(key);
        if (!currentValue.equals(newValue)) {
            throw new AssertionError("Expected current value to be '" + newValue + "', but got '" + currentValue + "'");
        }
    }

    private static void testReplaceNonExistingKey() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "nonexistent";
        String value = "value";
        String result = map.replace(key, value);
        if (result != null) {
            throw new AssertionError("Expected null when replacing non-existing key, but got '" + result + "'");
        }
        String currentValue = map.get(key);
        if (currentValue != null) {
            throw new AssertionError("Expected null for non-existing key, but got '" + currentValue + "'");
        }
    }
}
