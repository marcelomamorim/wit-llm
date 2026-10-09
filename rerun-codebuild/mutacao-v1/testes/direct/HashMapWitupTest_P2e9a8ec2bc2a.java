/* @test    @summary Test HashMap.replaceAll for correctness and exception handling    @run main HashMapWitupTest_P2e9a8ec2bc2a */

import java.util.HashMap;
import java.util.function.BiFunction;

public class HashMapWitupTest_P2e9a8ec2bc2a {
    public static void main(String[] args) {
        testReplaceAllBasic();
        testReplaceAllNullFunction();
        testReplaceAllConcurrentModification();
    }

    static void testReplaceAllBasic() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        BiFunction<String, Integer, Integer> func = (k, v) -> v * 10;
        map.replaceAll(func);
        if (map.get("a") != 10 || map.get("b") != 20 || map.get("c") != 30) {
            throw new AssertionError("Basic replaceAll failed") ;
        }
    }

    static void testReplaceAllNullFunction() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("x", 5);
        try {
            map.replaceAll(null);
            throw new AssertionError("Expected NullPointerException") ;
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testReplaceAllConcurrentModification() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("k1", 1);
        map.put("k2", 2);
        BiFunction<String, Integer, Integer> func = (k, v) -> {
            if (k.equals("k1")) {
                map.remove("k2");
                return v;
            }
            return v;
        };
        try {
            map.replaceAll(func);
            throw new AssertionError("Expected ConcurrentModificationException") ;
        } catch (java.util.ConcurrentModificationException expected) {
            // expected
        }
    }
}
