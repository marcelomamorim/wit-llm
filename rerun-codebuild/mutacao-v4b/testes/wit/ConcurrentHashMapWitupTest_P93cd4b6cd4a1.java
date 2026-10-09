/* @test
   @summary Testa o método containsValue de ConcurrentHashMap para comportamento esperado.
   @run main ConcurrentHashMapWitupTest_P93cd4b6cd4a1
 */
import java.util.concurrent.ConcurrentHashMap;
public class ConcurrentHashMapWitupTest_P93cd4b6cd4a1 {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        // Testa valor existente
        if (!map.containsValue("value1")) {
            throw new AssertionError("Expected to find value1 in map");
        }
        // Testa valor não existente
        if (map.containsValue("value3")) {
            throw new AssertionError("Did not expect to find value3 in map");
        }
        // Testa valor null, deve lançar NullPointerException
        try {
            map.containsValue(null);
            throw new AssertionError("Expected NullPointerException for null value");
        } catch (NullPointerException e) {
            // esperado
        }
    }
}
