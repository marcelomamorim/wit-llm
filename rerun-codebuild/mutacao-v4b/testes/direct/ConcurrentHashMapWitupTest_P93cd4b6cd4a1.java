/* @test
   @summary Testa o método containsValue para verificar se o valor existe no mapa.
   @run main ConcurrentHashMapWitupTest_P93cd4b6cd4a1
 */
import java.util.concurrent.ConcurrentHashMap;
public class ConcurrentHashMapWitupTest_P93cd4b6cd4a1 {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        // Testa valor existente
        boolean containsValue1 = map.containsValue("value1");
        if (!containsValue1) {
            throw new AssertionError("Expected map to contain value 'value1'");
        }
        // Testa valor inexistente
        boolean containsValue3 = map.containsValue("value3");
        if (containsValue3) {
            throw new AssertionError("Did not expect map to contain value 'value3'");
        }
        // Testa valor nulo lança NullPointerException
        try {
            map.containsValue(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            // esperado
        }
    }
}
