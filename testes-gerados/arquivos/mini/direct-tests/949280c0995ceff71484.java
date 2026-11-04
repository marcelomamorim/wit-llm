/*
 * @test
 * @summary Test ConcurrentHashMap.forEach with various scenarios
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.Node;
import java.util.function.Consumer;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachEmptyMap();
        testForEachWithSingleEntry();
        testForEachWithMultipleEntries();
    }

    static void testForEachWithNullAction() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.forEach(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachEmptyMap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        final int[] count = {0};
        map.forEach(new Consumer<Map.Entry<String, String>>() {
            public void accept(Map.Entry<String, String> e) {
                count[0]++;
            }
        });
        if (count[0] != 0) {
            throw new RuntimeException("forEach on empty map should not invoke action");
        }
    }

    static void testForEachWithSingleEntry() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        final int[] count = {0};
        final String[] key = new String[1];
        final String[] value = new String[1];
        map.forEach(new Consumer<Map.Entry<String, String>>() {
            public void accept(Map.Entry<String, String> e) {
                count[0]++;
                key[0] = e.getKey();
                value[0] = e.getValue();
            }
        });
        if (count[0] != 1) {
            throw new RuntimeException("forEach should invoke action exactly once");
        }
        if (!"key1".equals(key[0]) || !"value1".equals(value[0])) {
            throw new RuntimeException("forEach action received wrong entry");
        }
    }

    static void testForEachWithMultipleEntries() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("k1", "v1");
        map.put("k2", "v2");
        map.put("k3", "v3");
        final int[] count = {0};
        final StringBuilder keys = new StringBuilder();
        final StringBuilder values = new StringBuilder();
        map.forEach(new Consumer<Map.Entry<String, String>>() {
            public void accept(Map.Entry<String, String> e) {
                count[0]++;
                keys.append(e.getKey());
                values.append(e.getValue());
            }
        });
        if (count[0] != 3) {
            throw new RuntimeException("forEach should invoke action for all entries");
        }
        String k = keys.toString();
        String v = values.toString();
        if (!(k.contains("k1") && k.contains("k2") && k.contains("k3"))) {
            throw new RuntimeException("forEach keys missing entries");
        }
        if (!(v.contains("v1") && v.contains("v2") && v.contains("v3"))) {
            throw new RuntimeException("forEach values missing entries");
        }
    }
}
