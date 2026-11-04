/* @test    @summary Test IdentityHashMap forEach method behavior    @run main IdentityHashMapWitupTest */

import java.util.IdentityHashMap;
import java.util.function.BiConsumer;

public class IdentityHashMapWitupTest {

    public static void main(String[] args) {
        testForEachWithEntries();
        testForEachWithEmptyMap();
        testForEachWithNullKey();
    }

    public static void testForEachWithEntries() {
        IdentityHashMap<String, Integer> map = new IdentityHashMap<>();
        String key1 = new String("key1");
        String key2 = new String("key2");
        map.put(key1, 1);
        map.put(key2, 2);
        StringBuilder resultBuilder = new StringBuilder();
        map.forEach((k, v) -> {
            resultBuilder.append(k).append(':').append(v).append(';');
        });
        String result = resultBuilder.toString();
        boolean hasKey1 = false;
        boolean hasKey2 = false;
        if (result.contains("key1:1")) {
            hasKey1 = true;
        }
        if (result.contains("key2:2")) {
            hasKey2 = true;
        }
        assert hasKey1 : "Expected key1 in forEach output";
        assert hasKey2 : "Expected key2 in forEach output";
    }

    public static void testForEachWithEmptyMap() {
        IdentityHashMap<String, String> emptyMap = new IdentityHashMap<>();
        final boolean[] called = {false};
        emptyMap.forEach((k, v) -> {
            called[0] = true;
        });
        assert !called[0] : "forEach should not call accept on empty map";
    }

    public static void testForEachWithNullKey() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        map.put(null, "nullValue");
        final boolean[] called = {false};
        map.forEach((k, v) -> {
            if (k == null && v.equals("nullValue")) {
                called[0] = true;
            }
        });
        assert called[0] : "forEach should process null key";
    }
}
