/*
 * @test
 * @summary Test WeakHashMap.replaceAll with null function and concurrent modification
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.ConcurrentModificationException;
import java.util.function.BiFunction;
import java.util.Objects;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        testReplaceAllNullFunction();
        testReplaceAllConcurrentModification();
        testReplaceAllBasicFunctionality();
    }

    private static void testReplaceAllNullFunction() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        try {
            map.replaceAll(null);
            throw new RuntimeException("Expected NullPointerException for null function");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testReplaceAllConcurrentModification() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        BiFunction<String, String, String> func = new BiFunction<String, String, String>() {
            private int count = 0;
            @Override
            public String apply(String k, String v) {
                count++;
                if (count == 1) {
                    // Modify map during replaceAll to trigger ConcurrentModificationException
                    map.put("key3", "value3");
                }
                return v + "_modified";
            }
        };

        try {
            map.replaceAll(func);
            throw new RuntimeException("Expected ConcurrentModificationException due to concurrent modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    private static void testReplaceAllBasicFunctionality() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("a", "1");
        map.put("b", "2");

        BiFunction<String, String, String> func = new BiFunction<String, String, String>() {
            @Override
            public String apply(String k, String v) {
                return v + v;
            }
        };

        map.replaceAll(func);

        String valA = map.get("a");
        String valB = map.get("b");

        if (!"11".equals(valA) || !"22".equals(valB)) {
            throw new RuntimeException("replaceAll did not update values as expected");
        }
    }
}
