/* @test    @summary Test HashMap.replaceAll for correctness and exception handling    @run main HashMapWitupTest */

import java.util.HashMap;
import java.util.function.BiFunction;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithValidFunction();
        testReplaceAllWithNullFunction();
        testReplaceAllWithConcurrentModification();
    }

    static void testReplaceAllWithValidFunction() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        BiFunction<String, Integer, Integer> func = (k, v) -> v * 10;
        map.replaceAll(func);
        assert map.get("a") == 10 : "Expected 10";
        assert map.get("b") == 20 : "Expected 20";
        assert map.get("c") == 30 : "Expected 30";
    }

    static void testReplaceAllWithNullFunction() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("x", 5);
        try {
            map.replaceAll(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testReplaceAllWithConcurrentModification() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("k1", 1);
        map.put("k2", 2);
        BiFunction<String, Integer, Integer> func = (k, v) -> {
            if (k.equals("k1")) {
                map.put("k3", 3); // modify during iteration
                return v;
            }
            return v + 1;
        };
        try {
            map.replaceAll(func);
            assert false : "Expected ConcurrentModificationException";
        } catch (java.util.ConcurrentModificationException e) {
            // expected
        }
    }
}
