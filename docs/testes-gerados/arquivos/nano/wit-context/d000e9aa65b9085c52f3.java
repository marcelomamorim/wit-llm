/*
 * @test
 * @summary Tests for LinkedHashMap.forEach method, including null action.
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
            // If no exception, test fails
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testForEachWithValidAction() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = key -> sb.append(key);
        map.forEach(consumer);
        String result = sb.toString();
        if (!result.contains("a") || !result.contains("b")) {
            throw new AssertionError("forEach did not process all keys: " + result);
        }
    }
}
