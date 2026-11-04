/* @test    @summary Test LinkedHashMap forEach method with a simple consumer    @run main LinkedHashMapWitupTest */

import java.util.LinkedHashMap;
import java.util.function.Consumer;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testForEachWithNonEmptyMap();
        testForEachWithEmptyMap();
        testForEachWithNullConsumer();
    }

    static void testForEachWithNonEmptyMap() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = (value) -> result.append(value).append(",");
        map.forEach(consumer);
        String expected = "1,2,3,";
        if (!result.toString().equals(expected)) {
            throw new AssertionError("Expected: " + expected + " but got: " + result);
        }
    }

    static void testForEachWithEmptyMap() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        StringBuilder result = new StringBuilder();
        Consumer<Integer> consumer = (value) -> result.append(value).append(",");
        map.forEach(consumer);
        String expected = "";
        if (!result.toString().equals(expected)) {
            throw new AssertionError("Expected empty string but got: " + result);
        }
    }

    static void testForEachWithNullConsumer() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("x", 10);
        boolean exceptionThrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException to be thrown when passing null consumer");
        }
    }
}
