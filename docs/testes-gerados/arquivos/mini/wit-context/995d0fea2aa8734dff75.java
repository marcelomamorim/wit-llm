/*
 * @test
 * @summary Test LinkedHashMap.replaceAll with null and normal function
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;
import java.util.function.BiFunction;
import java.util.ConcurrentModificationException;

public class LinkedHashMapWitupTest {
    public static void main(String[] args) {
        testReplaceAllNullFunction();
        testReplaceAllNormalFunction();
        testReplaceAllConcurrentModification();
    }

    private static void testReplaceAllNullFunction() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("key1", "value1");
        try {
            map.replaceAll(null);
            throw new Error("Expected NullPointerException for null function");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testReplaceAllNormalFunction() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        BiFunction<String, Integer, Integer> increment = new BiFunction<String, Integer, Integer>() {
            public Integer apply(String k, Integer v) {
                return v + 1;
            }
        };

        map.replaceAll(increment);

        if (map.get("a") != 2 || map.get("b") != 3 || map.get("c") != 4) {
            throw new Error("replaceAll did not correctly update values");
        }
    }

    private static void testReplaceAllConcurrentModification() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("x", "X");
        map.put("y", "Y");

        BiFunction<String, String, String> func = new BiFunction<String, String, String>() {
            public String apply(String k, String v) {
                if ("x".equals(k)) {
                    map.put("z", "Z"); // structural modification during replaceAll
                }
                return v.toLowerCase();
            }
        };

        try {
            map.replaceAll(func);
            throw new Error("Expected ConcurrentModificationException due to structural modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
