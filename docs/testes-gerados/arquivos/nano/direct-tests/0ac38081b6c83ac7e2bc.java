/*
 * @test    @summary Test for ConcurrentHashMap forEachRemaining method
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingWithEmptyMap();
    }

    static void testForEachRemainingWithElements() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = (val) -> result.append(val).append(",");
        map.forEachRemaining(consumer);
        String output = result.toString();
        boolean condition = output.contains("1,") && output.contains("2,") && output.contains("3,");
        if (!condition) {
            throw new AssertionError("Expected values not found in output: " + output);
        }
    }

    static void testForEachRemainingWithEmptyMap() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = (val) -> result.append(val).append(",");
        map.forEachRemaining(consumer);
        if (result.length() != 0) {
            throw new AssertionError("Expected no output for empty map, but got: " + result.toString());
        }
    }
}
