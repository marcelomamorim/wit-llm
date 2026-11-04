/*
 * @test
 * @summary Test for LinkedHashMap.replaceAll method behavior and exception handling
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;
import java.util.function.BiFunction;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithNullFunction();
        testReplaceAllWithValidFunction();
        testReplaceAllWithConcurrentModification();
    }

    private static void testReplaceAllWithNullFunction() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        boolean threwException = false;
        try {
            map.replaceAll(null);
        } catch (NullPointerException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected NullPointerException when passing null function");
        }
    }

    private static void testReplaceAllWithValidFunction() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.replaceAll((k, v) -> v + 10);
        if (map.get("a") != 11 || map.get("b") != 12) {
            throw new AssertionError("replaceAll did not update values as expected");
        }
    }

    private static void testReplaceAllWithConcurrentModification() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        boolean threwException = false;
        try {
            map.replaceAll((k, v) -> {
                if (k.equals("a")) {
                    map.put("c", 3); // Modifying map during iteration
                }
                return v + 1;
            });
        } catch (java.util.ConcurrentModificationException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected ConcurrentModificationException during replaceAll with concurrent modification");
        }
    }
}
