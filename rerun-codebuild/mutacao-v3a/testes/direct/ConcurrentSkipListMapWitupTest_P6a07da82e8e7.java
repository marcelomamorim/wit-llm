/*
 * @test
 * @summary Test forEach method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest_P6a07da82e8e7
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiConsumer;

public class ConcurrentSkipListMapWitupTest_P6a07da82e8e7 {

    public static void main(String[] args) {
        testForEachWithEntries();
        testForEachWithEmptyMap();
    }

    static void testForEachWithEntries() {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        StringBuilder result = new StringBuilder();
        BiConsumer<String, Integer> consumer = (k, v) -> result.append(k).append(v);
        map.forEach(consumer);
        String output = result.toString();
        boolean condition = output.contains("a1") && output.contains("b2") && output.contains("c3") && output.length() == 6;
        if (!condition) {
            throw new AssertionError("Unexpected output: " + output);
        }
    }

    static void testForEachWithEmptyMap() {
        ConcurrentSkipListMap<String, Integer> emptyMap = new ConcurrentSkipListMap<>();
        StringBuilder result = new StringBuilder();
        BiConsumer<String, Integer> consumer = (k, v) -> result.append(k).append(v);
        emptyMap.forEach(consumer);
        if (result.length() != 0) {
            throw new AssertionError("Expected empty result, but got: " + result.toString());
        }
    }
}
