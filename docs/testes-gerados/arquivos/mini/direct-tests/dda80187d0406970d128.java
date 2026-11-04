/*
 * @test
 * @summary Test IdentityHashMap.replaceAll with various BiFunction inputs and concurrent modification
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;
import java.util.ConcurrentModificationException;
import java.util.Objects;
import java.util.function.BiFunction;

public class IdentityHashMapWitupTest {
    public static void main(String[] args) {
        testReplaceAllBasic();
        testReplaceAllNullFunction();
        testReplaceAllConcurrentModification();
    }

    private static void testReplaceAllBasic() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        map.put("a", "1");
        map.put("b", "2");

        BiFunction<String, String, String> concatFunction = new BiFunction<String, String, String>() {
            public String apply(String k, String v) {
                return k + v;
            }
        };

        map.replaceAll(concatFunction);

        // Check that values have been replaced correctly
        for (String key : map.keySet()) {
            String value = map.get(key);
            if (!value.equals(key + (key.equals("a") ? "1" : "2"))) {
                throw new AssertionError("replaceAll did not update value correctly for key " + key);
            }
        }
    }

    private static void testReplaceAllNullFunction() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        map.put("key", "value");

        try {
            map.replaceAll(null);
            throw new AssertionError("Expected NullPointerException when passing null function");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testReplaceAllConcurrentModification() {
        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        map.put("x", "X");
        map.put("y", "Y");

        BiFunction<String, String, String> modFunction = new BiFunction<String, String, String>() {
            private boolean firstCall = true;
            public String apply(String k, String v) {
                if (firstCall) {
                    firstCall = false;
                    // Modify map during replaceAll to trigger ConcurrentModificationException
                    map.put("z", "Z");
                }
                return v.toLowerCase();
            }
        };

        try {
            map.replaceAll(modFunction);
            throw new AssertionError("Expected ConcurrentModificationException due to modification during replaceAll");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
