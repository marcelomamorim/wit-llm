/*
 * @test
 * @summary Test LinkedHashMap.forEach method for null action and concurrent modification
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;
import java.util.ConcurrentModificationException;
import java.util.function.BiConsumer;

public class LinkedHashMapWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachIterationAndConcurrentModification();
    }

    private static void testForEachNullAction() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("key1", "value1");
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachIterationAndConcurrentModification() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("k1", "v1");
        map.put("k2", "v2");

        final StringBuilder sb = new StringBuilder();
        BiConsumer<String, String> action = new BiConsumer<String, String>() {
            public void accept(String k, String v) {
                sb.append(k).append("=").append(v).append(",");
            }
        };

        // Normal iteration
        map.forEach(action);
        String result = sb.toString();
        if (!result.equals("k1=v1,k2=v2,")) {
            throw new AssertionError("Unexpected iteration order or content: " + result);
        }

        // Test concurrent modification detection
        sb.setLength(0);
        BiConsumer<String, String> actionMod = new BiConsumer<String, String>() {
            public void accept(String k, String v) {
                sb.append(k).append("=").append(v).append(",");
                if (k.equals("k1")) {
                    map.put("k3", "v3"); // structural modification during iteration
                }
            }
        };

        try {
            map.forEach(actionMod);
            throw new AssertionError("Expected ConcurrentModificationException due to modification during forEach");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
