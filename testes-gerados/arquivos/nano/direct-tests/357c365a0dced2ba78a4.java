/* @test    @summary Test for Collections.forEach to ensure it applies the consumer to each entry.    @run main CollectionsWitupTest */

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Consumer;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testForEachAppliesConsumer();
        testForEachWithNullConsumer();
    }

    static void testForEachAppliesConsumer() {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        StringBuilder result = new StringBuilder();
        Consumer<Map.Entry<String, Integer>> consumer = entry -> {
            result.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
        };
        Collections.forEach(consumer);
        String output = result.toString();
        boolean condition = output.contains("a:1") && output.contains("b:2") && output.contains("c:3");
        if (!condition) {
            throw new AssertionError("Consumer was not applied to all entries: " + output);
        }
    }

    static void testForEachWithNullConsumer() {
        boolean exceptionThrown = false;
        try {
            Collections.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null consumer");
        }
    }
}
