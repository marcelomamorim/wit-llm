/* @test
   @summary Testa o método forEach de ConcurrentHashMap para comportamento observável.
   @run main ConcurrentHashMapWitupTest_Pbc3ca0f1dd8f
 */
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class ConcurrentHashMapWitupTest_Pbc3ca0f1dd8f {
    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithValidAction();
    }

    private static void testForEachWithNullAction() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        boolean exceptionThrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    private static void testForEachWithValidAction() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        StringBuilder result = new StringBuilder();
        BiConsumer<String, Integer> consumer = (k, v) -> result.append(k).append(v);
        map.forEach(consumer);
        String combined = result.toString();
        if (!(combined.contains("a1") && combined.contains("b2"))) {
            throw new AssertionError("forEach did not process all entries correctly. Result: " + combined);
        }
    }
}
