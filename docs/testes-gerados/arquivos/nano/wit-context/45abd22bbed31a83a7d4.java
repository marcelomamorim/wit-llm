/* @test    @summary Test HashMap forEach method with null Consumer    @run main HashMapWitupTest */

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testForEachWithNullConsumer();
        testForEachWithValidConsumer();
    }

    static void testForEachWithNullConsumer() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        boolean exceptionThrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when calling forEach with null");
        }
    }

    static void testForEachWithValidConsumer() {
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        StringBuilder sb = new StringBuilder();
        Consumer<Map.Entry<String, String>> consumer = entry -> sb.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
        map.forEach(consumer);
        String result = sb.toString();
        if (!result.contains("a:1") || !result.contains("b:2")) {
            throw new AssertionError("forEach did not process entries correctly: " + result);
        }
    }
}
