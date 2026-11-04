/* @test    @summary Test replaceAll method of LinkedHashMap for correct behavior and exception handling    @run main LinkedHashMapWitupTest */

import java.util.LinkedHashMap;
import java.util.function.BiFunction;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithValidFunction();
        testReplaceAllWithNullFunction();
        testReplaceAllConcurrentModification();
    }

    static void testReplaceAllWithValidFunction() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.replaceAll((k, v) -> v + 10);
        assert map.get("a") == 11 : "Expected 11, got " + map.get("a");
        assert map.get("b") == 12 : "Expected 12, got " + map.get("b");
    }

    static void testReplaceAllWithNullFunction() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("x", 5);
        try {
            map.replaceAll(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testReplaceAllConcurrentModification() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("k1", 1);
        map.put("k2", 2);
        BiFunction<String, Integer, Integer> func = (k, v) -> {
            if (k.equals("k1")) {
                map.put("k3", 3); // modify map during iteration
                return v;
            }
            return v;
        };
        try {
            map.replaceAll(func);
            assert false : "Expected ConcurrentModificationException";
        } catch (ConcurrentModificationException e) {
            // expected
        }
    }
}
