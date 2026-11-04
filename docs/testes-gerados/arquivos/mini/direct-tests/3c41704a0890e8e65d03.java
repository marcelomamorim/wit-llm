/*
 * @test
 * @summary Test ConcurrentSkipListMap.replaceAll with various BiFunction inputs
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiFunction;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testReplaceAllBasic();
        testReplaceAllNullFunction();
        testReplaceAllFunctionReturnsNull();
    }

    static void testReplaceAllBasic() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");

        BiFunction<String, String, String> appendX = new BiFunction<String, String, String>() {
            public String apply(String k, String v) {
                return v + "x";
            }
        };

        map.replaceAll(appendX);

        if (!"1x".equals(map.get("a")))
            throw new AssertionError("Expected value '1x' for key 'a', got: " + map.get("a"));
        if (!"2x".equals(map.get("b")))
            throw new AssertionError("Expected value '2x' for key 'b', got: " + map.get("b"));
        if (!"3x".equals(map.get("c")))
            throw new AssertionError("Expected value '3x' for key 'c', got: " + map.get("c"));
    }

    static void testReplaceAllNullFunction() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        try {
            map.replaceAll(null);
            throw new AssertionError("Expected NullPointerException for null function");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testReplaceAllFunctionReturnsNull() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key", "value");

        BiFunction<String, String, String> returnsNull = new BiFunction<String, String, String>() {
            public String apply(String k, String v) {
                return null;
            }
        };

        try {
            map.replaceAll(returnsNull);
            throw new AssertionError("Expected NullPointerException when function returns null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
