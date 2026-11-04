/*
 * @test
 * @summary Tests for java.util.Collections methods, focusing on forEach behavior.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Consumer;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithValidAction();
    }

    private static void testForEachWithNullAction() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        try {
            Collections.forEach(map.entrySet(), null);
            // If no exception, test fails
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testForEachWithValidAction() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        StringBuilder sb = new StringBuilder();
        Consumer<Map.Entry<String, String>> action = entry -> sb.append(entry.getKey()).append(":").append(entry.getValue()).append(";");
        Collections.forEach(map.entrySet(), action);
        String result = sb.toString();
        if (!result.contains("key1:value1") || !result.contains("key2:value2")) {
            throw new AssertionError("forEach did not process all entries correctly");
        }
    }
}
