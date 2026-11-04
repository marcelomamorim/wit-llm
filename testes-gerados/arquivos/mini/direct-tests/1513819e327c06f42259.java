/*
 * @test
 * @summary Test WeakHashMap.replaceAll with various BiFunction inputs and concurrent modification
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.Map;
import java.util.ConcurrentModificationException;
import java.util.function.BiFunction;
import java.util.Objects;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        testReplaceAllBasic();
        testReplaceAllNullFunction();
        testReplaceAllConcurrentModification();
    }

    private static void testReplaceAllBasic() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        map.put(null, "nullValue");

        BiFunction<String, String, String> concatFunction = new BiFunction<String, String, String>() {
            public String apply(String k, String v) {
                return k + v;
            }
        };

        map.replaceAll(concatFunction);

        // Check that values have been replaced correctly
        if (!"a1".equals(map.get("a"))) {
            throw new AssertionError("Expected value 'a1' for key 'a', got: " + map.get("a"));
        }
        if (!"b2".equals(map.get("b"))) {
            throw new AssertionError("Expected value 'b2' for key 'b', got: " + map.get("b"));
        }
        if (!"nullnullValue".equals(map.get(null))) {
            throw new AssertionError("Expected value 'nullnullValue' for key null, got: " + map.get(null));
        }
    }

    private static void testReplaceAllNullFunction() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key", "value");

        try {
            map.replaceAll(null);
            throw new AssertionError("Expected NullPointerException for null function");
        } catch (NullPointerException expected) {
            // Expected
        }
    }

    private static void testReplaceAllConcurrentModification() {
        final WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("x", "1");
        map.put("y", "2");

        BiFunction<String, String, String> modFunction = new BiFunction<String, String, String>() {
            private boolean modified = false;
            public String apply(String k, String v) {
                if (!modified) {
                    map.put("z", "3"); // This will modify the map and modCount
                    modified = true;
                }
                return v;
            }
        };

        try {
            map.replaceAll(modFunction);
            throw new AssertionError("Expected ConcurrentModificationException due to modification during replaceAll");
        } catch (ConcurrentModificationException expected) {
            // Expected
        }
    }
}
