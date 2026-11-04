/* @test    @summary Test HashMap.replaceAll with null function    @run main HashMapWitupTest */

import java.util.HashMap;
import java.util.function.BiFunction;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithNullFunction();
        testReplaceAllWithValidFunction();
        testReplaceAllWithEmptyMap();
    }

    static void testReplaceAllWithNullFunction() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        boolean exceptionThrown = false;
        try {
            map.replaceAll(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when replacing all with null function");
        }
    }

    static void testReplaceAllWithValidFunction() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.replaceAll((k, v) -> v + 10);
        if (map.get("a") != 11 || map.get("b") != 12) {
            throw new AssertionError("replaceAll did not apply function correctly");
        }
    }

    static void testReplaceAllWithEmptyMap() {
        HashMap<String, Integer> map = new HashMap<>();
        map.replaceAll((k, v) -> v + 10); // should not throw
        if (!map.isEmpty()) {
            throw new AssertionError("Empty map should remain empty after replaceAll");
        }
    }
}
