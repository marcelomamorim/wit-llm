/* @test
   @summary Testa o método replaceAll de ConcurrentHashMap para comportamento observável.
   @run main ConcurrentHashMapWitupTest_P533f640daf49
 */
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ConcurrentHashMapWitupTest_P533f640daf49 {
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
            throw new AssertionError("Expected NullPointerException when function is null");
        }
    }

    private static void testReplaceAllWithValidFunction() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.replaceAll((k, v) -> v + 10);
        if (map.get("a") != 11 || map.get("b") != 12) {
            throw new AssertionError("replaceAll did not update values as expected");
        }
    }
}
