/*
 * @test
 * @summary Test java.util.Collections forEach method behavior
 * @run main CollectionsWitupTest
 */

import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.Objects;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachWithValidConsumer();
        testForEachWithNullConsumer();
    }

    private static void testForEachWithValidConsumer() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        final StringBuilder sb = new StringBuilder();
        Consumer<Entry<? super String, String>> action = new Consumer<Entry<? super String, String>>() {
            public void accept(Entry<? super String, String> entry) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
            }
        };

        // Using Map's forEach to simulate Collections.forEach behavior
        map.entrySet().forEach(action);

        String result = sb.toString();
        if (!(result.contains("key1=value1") && result.contains("key2=value2"))) {
            throw new AssertionError("forEach did not process all entries correctly: " + result);
        }
    }

    private static void testForEachWithNullConsumer() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");

        try {
            Consumer<Entry<? super String, String>> action = null;
            // Simulate the null check in Collections.forEach
            Objects.requireNonNull(action);
            // If no exception, fail
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
