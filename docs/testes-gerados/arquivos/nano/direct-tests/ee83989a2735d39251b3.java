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
        Consumer<String> consumer = key -> result.append(key);
        map.forEachRemaining(consumer);
        String output = result.toString();
        boolean condition = output.contains("a") && output.contains("b") && output.contains("c");
        if (!condition) {
            throw new AssertionError("Expected keys not found in output: " + output);
        }
    }

    static void testForEachRemainingWithEmptyMap() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        StringBuilder result = new StringBuilder();
        Consumer<String> consumer = key -> result.append(key);
        map.forEachRemaining(consumer);
        if (result.length() != 0) {
            throw new AssertionError("Expected no keys, but got: " + result.toString());
        }
    }
}
