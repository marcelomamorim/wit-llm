/*
 * @test
 * @summary Test IdentityHashMap.replaceAll with null function and concurrent modification
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;
import java.util.ConcurrentModificationException;
import java.util.function.BiFunction;
import java.util.Objects;

public class IdentityHashMapWitupTest {
    public static void main(String[] args) {
        testReplaceAllNullFunction();
        testReplaceAllConcurrentModification();
        testReplaceAllBasicFunctionality();
    }

    private static void testReplaceAllNullFunction() {
        IdentityHashMap<Object, Object> map = new IdentityHashMap<>();
        try {
            map.replaceAll(null);
            throw new Error("Expected NullPointerException for null function");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testReplaceAllConcurrentModification() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
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
                return v + "_modified";
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
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        map.put("a", "1");
        map.put("b", "2");

        BiFunction<String, String, String> func = new BiFunction<String, String, String>() {
            @Override
            public String apply(String k, String v) {
                return v + v;
            }
        };

        map.replaceAll(func);

        // Check that values were replaced correctly
        if (!"11".equals(map.get("a"))) {
            throw new Error("Value for key 'a' was not replaced correctly");
        }
        if (!"22".equals(map.get("b"))) {
            throw new Error("Value for key 'b' was not replaced correctly");
        }
    }
}
