/* @test
   @summary Testa o método computeIfAbsent de ConcurrentHashMap para comportamento esperado de NullPointerException.
   @run main ConcurrentHashMapWitupTest_P35e78a57a2f1
 */
import java.util.concurrent.ConcurrentHashMap;
public class ConcurrentHashMapWitupTest_P35e78a57a2f1 {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        // Testa quando key é null
        try {
            map.computeIfAbsent(null, k -> "value");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) { /* esperado */ }
        // Testa quando mappingFunction é null
        try {
            map.computeIfAbsent("key", null);
            throw new AssertionError("Expected NullPointerException for null mappingFunction");
        } catch (NullPointerException expected) { /* esperado */ }
    }
}
