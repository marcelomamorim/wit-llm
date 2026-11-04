/*
 * @test
 * @summary Test IdentityHashMap.forEach method for null action and concurrent modification
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;
import java.util.ConcurrentModificationException;
import java.util.function.BiConsumer;
import java.util.Objects;

public class IdentityHashMapWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachConcurrentModification();
        testForEachNormalInvocation();
    }

    private static void testForEachNullAction() {
        IdentityHashMap<Object, Object> map = new IdentityHashMap<>();
        try {
            map.forEach(null);
            throw new RuntimeException("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachConcurrentModification() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        final BiConsumer<String, String> action = new BiConsumer<String, String>() {
            private int count = 0;
            @Override
            public void accept(String k, String v) {
                count++;
                if (count == 1) {
                    // Modify map during iteration to trigger ConcurrentModificationException
                    map.put("key3", "value3");
                }
            }
        };

        try {
            map.forEach(action);
            throw new RuntimeException("Expected ConcurrentModificationException due to concurrent modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    private static void testForEachNormalInvocation() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        map.put("a", "1");
        map.put("b", "2");

        final StringBuilder sb = new StringBuilder();
        map.forEach(new BiConsumer<String, String>() {
            @Override
            public void accept(String k, String v) {
                sb.append(k).append("=").append(v).append(",");
            }
        });

        String result = sb.toString();
        if (!(result.contains("a=1") && result.contains("b=2"))) {
            throw new RuntimeException("forEach did not visit all entries: " + result);
        }
    }
}
