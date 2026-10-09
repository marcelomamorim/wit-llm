/* @test
   @summary Testa o método replace de ConcurrentSkipListMap para condições de entrada inválidas.
   @run main ConcurrentSkipListMapWitupTest_Pac96907de1bc
 */
import java.util.concurrent.ConcurrentSkipListMap;
public class ConcurrentSkipListMapWitupTest_Pac96907de1bc {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        String key = "key";
        String oldValue = "old";
        String newValue = "new";
        // Testa null key
        try {
            map.replace(null, oldValue, newValue);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException e) { /* esperado */ }
        // Testa null oldValue
        try {
            map.replace(key, null, newValue);
            throw new AssertionError("Expected NullPointerException for null oldValue");
        } catch (NullPointerException e) { /* esperado */ }
        // Testa null newValue
        try {
            map.replace(key, oldValue, null);
            throw new AssertionError("Expected NullPointerException for null newValue");
        } catch (NullPointerException e) { /* esperado */ }
        // Testa substituição bem-sucedida
        map.put(key, oldValue);
        boolean result = map.replace(key, oldValue, newValue);
        if (!result) {
            throw new AssertionError("Replace should succeed when oldValue matches");
        }
        if (!map.get(key).equals(newValue)) {
            throw new AssertionError("Value not updated correctly");
        }
        // Testa substituição falha por valor diferente
        boolean resultFail = map.replace(key, "incorrectOld", "anotherNew");
        if (resultFail) {
            throw new AssertionError("Replace should fail when oldValue does not match");
        }
        if (!map.get(key).equals(newValue)) {
            throw new AssertionError("Value should remain unchanged after failed replace");
        }
    }
}
