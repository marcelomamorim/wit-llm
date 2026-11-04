/*
 * @test
 * @summary Deterministic tests for java.util.TreeMap.replaceAll(BiFunction)
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.ConcurrentModificationException;
import java.util.function.BiFunction;
import java.util.Objects;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testReplaceAllNullFunction();
        testReplaceAllBasicFunctionality();
        testReplaceAllConcurrentModification();
    }

    private static void testReplaceAllNullFunction() {
        TreeMap<Integer, String> map = new TreeMap<>();
        try {
            map.replaceAll(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testReplaceAllBasicFunctionality() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        BiFunction<Integer, String, String> toUpper = new BiFunction<Integer, String, String>() {
            public String apply(Integer k, String v) {
                return v.toUpperCase();
            }
        };

        map.replaceAll(toUpper);

        if (!"ONE".equals(map.get(1)))
            throw new Error("replaceAll failed for key 1");
        if (!"TWO".equals(map.get(2)))
            throw new Error("replaceAll failed for key 2");
        if (!"THREE".equals(map.get(3)))
            throw new Error("replaceAll failed for key 3");
    }

    private static void testReplaceAllConcurrentModification() {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);

        BiFunction<Integer, Integer, Integer> modFunction = new BiFunction<Integer, Integer, Integer>() {
            private boolean firstCall = true;
            public Integer apply(Integer k, Integer v) {
                if (firstCall) {
                    firstCall = false;
                    map.put(4, 4); // structural modification during replaceAll
                }
                return v + 10;
            }
        };

        try {
            map.replaceAll(modFunction);
            throw new Error("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
