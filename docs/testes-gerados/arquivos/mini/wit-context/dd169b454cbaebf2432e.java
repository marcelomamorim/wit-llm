/*
 * @test
 * @summary Test ConcurrentHashMap.forEach(Consumer) for null argument and normal usage
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.Node;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachNormal();
    }

    private static void testForEachNullAction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean npeThrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    private static void testForEachNormal() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String v) {
                sb.append(v).append(",");
            }
        };

        map.forEach(action);

        String result = sb.toString();
        if (!(result.contains("value1") && result.contains("value2"))) {
            throw new AssertionError("forEach did not process all values: " + result);
        }
    }
}
