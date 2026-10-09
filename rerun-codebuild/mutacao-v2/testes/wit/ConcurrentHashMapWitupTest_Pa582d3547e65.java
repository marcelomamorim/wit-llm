/*
 * @test
 * @summary Test ConcurrentHashMap.forEach with null and normal action
 * @run main ConcurrentHashMapWitupTest_Pa582d3547e65
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class ConcurrentHashMapWitupTest_Pa582d3547e65 {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachNormalAction();
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

    private static void testForEachNormalAction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        final StringBuilder sb = new StringBuilder();
        BiConsumer<String, String> action = new BiConsumer<String, String>() {
            public void accept(String k, String v) {
                sb.append(k).append('=').append(v).append(';');
            }
        };

        map.forEach(action);

        String result = sb.toString();
        if (!(result.contains("key1=value1") && result.contains("key2=value2"))) {
            throw new AssertionError("forEach did not process all entries correctly: " + result);
        }
    }
}
