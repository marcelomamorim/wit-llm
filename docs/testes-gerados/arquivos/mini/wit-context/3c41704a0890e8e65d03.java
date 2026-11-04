/*
 * @test
 * @summary Test ConcurrentSkipListMap.replaceAll with null function and normal usage
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiFunction;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testReplaceAllNullFunction();
        testReplaceAllBasicFunctionality();
    }

    static void testReplaceAllNullFunction() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        boolean npeThrown = false;
        try {
            map.replaceAll(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when function is null");
        }
    }

    static void testReplaceAllBasicFunctionality() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");

        BiFunction<String, String, String> func = new BiFunction<String, String, String>() {
            public String apply(String k, String v) {
                // Append "-x" to each value
                return v + "-x";
            }
        };

        map.replaceAll(func);

        if (!"1-x".equals(map.get("a"))) {
            throw new AssertionError("Value for key 'a' was not replaced correctly");
        }
        if (!"2-x".equals(map.get("b"))) {
            throw new AssertionError("Value for key 'b' was not replaced correctly");
        }
        if (!"3-x".equals(map.get("c"))) {
            throw new AssertionError("Value for key 'c' was not replaced correctly");
        }
    }
}
