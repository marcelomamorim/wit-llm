/*
 * @test
 * @summary Test java.util.WeakHashMap.forEach method behavior including null action and concurrent modification
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.ConcurrentModificationException;
import java.util.function.BiConsumer;
import java.util.Objects;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachNormalIteration();
        testForEachConcurrentModification();
    }

    private static void testForEachNullAction() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachNormalIteration() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
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
            throw new AssertionError("forEach did not iterate all entries: " + result);
        }
    }

    private static void testForEachConcurrentModification() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key", "value");

        BiConsumer<String, String> action = new BiConsumer<String, String>() {
            public void accept(String k, String v) {
                // Modify map during iteration to trigger ConcurrentModificationException
                map.put("anotherKey", "anotherValue");
            }
        };

        try {
            map.forEach(action);
            throw new AssertionError("Expected ConcurrentModificationException due to concurrent modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
