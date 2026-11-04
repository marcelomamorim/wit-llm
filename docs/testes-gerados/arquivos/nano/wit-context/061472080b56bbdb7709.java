/*
 * @test
 * @summary Tests for java.util.LinkedHashMap
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithValidAction();
    }

    public static void testForEachWithNullAction() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        boolean exceptionThrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to forEach");
        }
    }

    public static void testForEachWithValidAction() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        StringBuilder sb = new StringBuilder();
        Consumer<Map.Entry<String, String>> consumer = entry -> sb.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
        map.forEach(consumer);
        String result = sb.toString();
        if (!result.contains("a:1") || !result.contains("b:2")) {
            throw new AssertionError("forEach did not process all entries correctly. Result: " + result);
        }
    }
}
