/* @test
   @summary Verifies the replaceAll method of ConcurrentHashMap.
   @run main ConcurrentHashMapWitupTest_P533f640daf49
 */
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ConcurrentHashMapWitupTest_P533f640daf49 {
    public static void main(String[] args) {
        testReplaceAllWithNonNullFunction();
        testReplaceAllWithNullFunction();
        testReplaceAllWithFunctionReturningNull();
    }

    private static void testReplaceAllWithNonNullFunction() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        BiFunction<String, Integer, Integer> func = (k, v) -> v * 10;
        map.replaceAll(func);
        assert map.get("a") == 10 : "Expected 10, got " + map.get("a");
        assert map.get("b") == 20 : "Expected 20, got " + map.get("b");
        assert map.get("c") == 30 : "Expected 30, got " + map.get("c");
    }

    private static void testReplaceAllWithNullFunction() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("x", 5);
        try {
            map.replaceAll(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testReplaceAllWithFunctionReturningNull() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("y", 7);
        BiFunction<String, Integer, Integer> func = (k, v) -> null;
        try {
            map.replaceAll(func);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
