/* @test    @summary Test replaceAll method of WeakHashMap    @run main WeakHashMapWitupTest */

import java.util.WeakHashMap;
import java.util.function.BiFunction;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        testReplaceAllWithNonNullValues();
        testReplaceAllWithNullValues();
    }

    static void testReplaceAllWithNonNullValues() {
        WeakHashMap<String, Integer> map = new WeakHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        BiFunction<String, Integer, Integer> func = (k, v) -> v + 10;
        map.replaceAll(func);
        assert map.get("a") == 11 : "Expected 11 for key 'a'";
        assert map.get("b") == 12 : "Expected 12 for key 'b";
    }

    static void testReplaceAllWithNullValues() {
        WeakHashMap<String, Integer> map = new WeakHashMap<>();
        map.put("x", null);
        map.put("y", 5);
        BiFunction<String, Integer, Integer> func = (k, v) -> v == null ? 0 : v + 5;
        map.replaceAll(func);
        assert map.get("x") == 0 : "Expected 0 for key 'x'";
        assert map.get("y") == 10 : "Expected 10 for key 'y'";
    }
}