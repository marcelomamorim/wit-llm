/*
 * @test
 * @summary Test for LinkedHashMap.forEach with null action
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;
import java.util.function.Consumer;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithValidAction();
    }

    static void testForEachWithNullAction() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        try {
            map.forEach(null);
            // If no exception, fail the test
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testForEachWithValidAction() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = v -> sb.append(v).append(",");
        map.forEach(consumer);
        String result = sb.toString();
        if (!result.contains("value1") || !result.contains("value2")) {
            throw new AssertionError("forEach did not process all values: " + result);
        }
    }
}
