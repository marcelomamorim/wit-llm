/* @test
   @summary Testa o método computeIfPresent de ConcurrentHashMap para entradas nulas.
   @run main ConcurrentHashMapWitupTest_Pfd99dc27c4c6
 */
import java.util.concurrent.ConcurrentHashMap;
public class ConcurrentHashMapWitupTest_Pfd99dc27c4c6 {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        // Testa com chave nula
        try {
            map.computeIfPresent(null, (k, v) -> v);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) { /* esperado */ }
        // Testa com função nula
        try {
            map.computeIfPresent("key", null);
            throw new AssertionError("Expected NullPointerException for null remappingFunction");
        } catch (NullPointerException expected) { /* esperado */ }
    }
}
