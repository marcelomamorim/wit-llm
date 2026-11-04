/*
 * @test
 * @summary Test forEachRemaining method in Collections class
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.Objects;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithValidConsumer();
        testForEachRemainingWithNullConsumer();
    }

    static void testForEachRemainingWithValidConsumer() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();

        final StringBuilder sb = new StringBuilder();
        Consumer<Map.Entry<String, String>> action = new Consumer<Map.Entry<String, String>>() {
            public void accept(Map.Entry<String, String> e) {
                sb.append(e.getKey()).append("=").append(e.getValue()).append(",");
            }
        };

        // Use the forEachRemaining method of the iterator
        it.forEachRemaining(action);

        String result = sb.toString();
        if (!(result.contains("key1=value1") && result.contains("key2=value2"))) {
            throw new AssertionError("forEachRemaining did not process all entries: " + result);
        }
    }

    static void testForEachRemainingWithNullConsumer() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");

        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();

        try {
            it.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException when passing null consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
