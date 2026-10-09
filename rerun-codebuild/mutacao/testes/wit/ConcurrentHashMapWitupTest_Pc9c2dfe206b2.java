/*
 * @test
 * @summary Tests for ConcurrentHashMap.replaceAll with null function
 * @run main ConcurrentHashMapWitupTest_Pc9c2dfe206b2
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ConcurrentHashMapWitupTest_Pc9c2dfe206b2 {

    public static void main(String[] args) {
        testReplaceAllWithNullFunction();
        testReplaceAllWithValidFunction();
    }

    private static void testReplaceAllWithNullFunction() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
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
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.replaceAll((k, v) -> v + 10);
        Integer valueA = map.get("a");
        Integer valueB = map.get("b");
        if (valueA == null || valueB == null) {
            throw new AssertionError("Values should not be null after replaceAll");
        }
        if (valueA != 11 || valueB != 12) {
            throw new AssertionError("Values not correctly replaced: a=" + valueA + ", b=" + valueB);
        }
    }
}
