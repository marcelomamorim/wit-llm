/* @test
   @summary Testa o método replaceAll de ConcurrentSkipListMap para comportamento esperado com entrada válida.
   @run main ConcurrentSkipListMapWitupTest_Pa8e49090aef6
 */
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiFunction;
import java.util.Map;

public class ConcurrentSkipListMapWitupTest_Pa8e49090aef6 {
    public static void main(String[] args) {
        testReplaceAllWithValidFunction();
        testReplaceAllWithNullFunctionThrows();
    }

    private static void testReplaceAllWithValidFunction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");
        BiFunction<? super Integer, ? super String, ? extends String> func = (k, v) -> v.toUpperCase();
        map.replaceAll(func);
        boolean testPassed = map.get(1).equals("A") && map.get(2).equals("B") && map.get(3).equals("C");
        if (!testPassed) {
            throw new AssertionError("replaceAll did not transform values as expected.");
        }
    }

    private static void testReplaceAllWithNullFunctionThrows() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        try {
            map.replaceAll(null);
            throw new AssertionError("Expected NullPointerException not thrown.");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
