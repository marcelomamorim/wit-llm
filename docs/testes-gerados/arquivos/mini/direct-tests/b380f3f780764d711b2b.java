/*
 * @test
 * @summary Test java.util.TreeMap.replaceAll method behavior
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.ConcurrentModificationException;
import java.util.function.BiFunction;
import java.util.Objects;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testReplaceAllBasic();
        testReplaceAllNullFunction();
        testReplaceAllConcurrentModification();
    }

    static void testReplaceAllBasic() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        BiFunction<String, Integer, Integer> addOne = new BiFunction<String, Integer, Integer>() {
            public Integer apply(String k, Integer v) {
                return v + 1;
            }
        };

        map.replaceAll(addOne);

        if (!map.get("a").equals(2))
            throw new AssertionError("Expected value 2 for key 'a', got " + map.get("a"));
        if (!map.get("b").equals(3))
            throw new AssertionError("Expected value 3 for key 'b', got " + map.get("b"));
        if (!map.get("c").equals(4))
            throw new AssertionError("Expected value 4 for key 'c', got " + map.get("c"));
    }

    static void testReplaceAllNullFunction() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("key", 10);
        try {
            map.replaceAll(null);
            throw new AssertionError("Expected NullPointerException for null function");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testReplaceAllConcurrentModification() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("x", 100);
        map.put("y", 200);

        BiFunction<String, Integer, Integer> func = new BiFunction<String, Integer, Integer>() {
            private boolean firstCall = true;
            public Integer apply(String k, Integer v) {
                if (firstCall) {
                    firstCall = false;
                    map.put("z", 300); // structural modification during iteration
                }
                return v;
            }
        };

        try {
            map.replaceAll(func);
            throw new AssertionError("Expected ConcurrentModificationException due to structural modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
