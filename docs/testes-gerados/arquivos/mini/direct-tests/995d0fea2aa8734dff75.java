/*
 * @test
 * @summary Test LinkedHashMap.replaceAll with various BiFunction inputs and concurrent modification
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;
import java.util.ConcurrentModificationException;
import java.util.function.BiFunction;

public class LinkedHashMapWitupTest {
    public static void main(String[] args) {
        testReplaceAllBasic();
        testReplaceAllNullFunction();
        testReplaceAllConcurrentModification();
    }

    static void testReplaceAllBasic() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        BiFunction<String, Integer, Integer> addOne = new BiFunction<String, Integer, Integer>() {
            public Integer apply(String k, Integer v) {
                return v + 1;
            }
        };

        map.replaceAll(addOne);

        if (!map.get("one").equals(2))
            throw new AssertionError("Expected value 2 for key 'one', got " + map.get("one"));
        if (!map.get("two").equals(3))
            throw new AssertionError("Expected value 3 for key 'two', got " + map.get("two"));
        if (!map.get("three").equals(4))
            throw new AssertionError("Expected value 4 for key 'three', got " + map.get("three"));
    }

    static void testReplaceAllNullFunction() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("key", "value");
        try {
            map.replaceAll(null);
            throw new AssertionError("Expected NullPointerException for null function");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testReplaceAllConcurrentModification() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("a", "A");
        map.put("b", "B");

        BiFunction<String, String, String> func = new BiFunction<String, String, String>() {
            public String apply(String k, String v) {
                if ("a".equals(k)) {
                    map.put("c", "C"); // structural modification during replaceAll
                }
                return v.toLowerCase();
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
