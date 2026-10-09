/*
 * @test
 * @summary Test java.util.HashMap.replaceAll method for null function and concurrent modification
 * @run main HashMapWitupTest_P183c0dc21e90
 */

import java.util.HashMap;
import java.util.ConcurrentModificationException;
import java.util.function.BiFunction;

public class HashMapWitupTest_P183c0dc21e90 {
    public static void main(String[] args) {
        testReplaceAllNullFunction();
        testReplaceAllConcurrentModification();
        testReplaceAllBasicFunctionality();
    }

    private static void testReplaceAllNullFunction() {
        HashMap<String, String> map = new HashMap<>();
        try {
            map.replaceAll(null);
            throw new Error("Expected NullPointerException for null function");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testReplaceAllConcurrentModification() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        BiFunction<String, String, String> func = new BiFunction<String, String, String>() {
            private boolean firstCall = true;
            @Override
            public String apply(String k, String v) {
                if (firstCall) {
                    firstCall = false;
                    // Modify map during replaceAll to trigger ConcurrentModificationException
                    map.put("key3", "value3");
                }
                return v.toUpperCase();
            }
        };

        try {
            map.replaceAll(func);
            throw new Error("Expected ConcurrentModificationException due to concurrent modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    private static void testReplaceAllBasicFunctionality() {
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");

        BiFunction<String, String, String> func = new BiFunction<String, String, String>() {
            @Override
            public String apply(String k, String v) {
                return v + v;
            }
        };

        map.replaceAll(func);

        if (!"11".equals(map.get("a"))) {
            throw new Error("Expected value '11' for key 'a', got: " + map.get("a"));
        }
        if (!"22".equals(map.get("b"))) {
            throw new Error("Expected value '22' for key 'b', got: " + map.get("b"));
        }
    }
}
