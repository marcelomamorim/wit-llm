/* @test
   @summary Testa o método replace com entradas nulas.
   @run main ConcurrentHashMapWitupTest_P9b9ded78feec
 */
import java.util.concurrent.ConcurrentHashMap;
public class ConcurrentHashMapWitupTest_P9b9ded78feec {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        // Testa replace com key nula
        try {
            map.replace(null, "old", "new");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException e) { /* esperado */ }
        // Testa replace com oldValue nulo
        try {
            map.replace("key", null, "new");
            throw new AssertionError("Expected NullPointerException for null oldValue");
        } catch (NullPointerException e) { /* esperado */ }
        // Testa replace com newValue nulo
        try {
            map.replace("key", "old", null);
            throw new AssertionError("Expected NullPointerException for null newValue");
        } catch (NullPointerException e) { /* esperado */ }
        // Testa replace com valores válidos
        map.put("key", "old");
        boolean result = map.replace("key", "old", "new");
        if (!result || !"new".equals(map.get("key"))) {
            throw new AssertionError("Replace não funcionou como esperado");
        }
    }
}
