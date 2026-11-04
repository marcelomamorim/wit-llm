/*
 * @test
 * @summary Tests for java.util.WeakHashMap.replaceAll method
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;
import java.util.function.BiFunction;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithNullFunction();
        testReplaceAllWithValidFunction();
    }

    private static void testReplaceAllWithNullFunction() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        boolean exceptionThrown = false;
        try {
            map.replaceAll(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null function");
        }
    }

    private static void testReplaceAllWithValidFunction() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        BiFunction<String, String, String> func = (k, v) -> v + v;
        map.replaceAll(func);
        if (!"11".equals(map.get("a")) || !"22".equals(map.get("b"))) {
            throw new AssertionError("replaceAll did not apply function correctly");
        }
    }
}
