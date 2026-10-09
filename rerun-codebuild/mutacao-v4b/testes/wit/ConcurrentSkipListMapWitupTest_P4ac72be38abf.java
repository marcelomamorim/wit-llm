/* @test
   @summary Testa o método containsValue de ConcurrentSkipListMap para verificar comportamento com valores nulos.
   @run main ConcurrentSkipListMapWitupTest_P4ac72be38abf
 */
import java.util.concurrent.ConcurrentSkipListMap;
public class ConcurrentSkipListMapWitupTest_P4ac72be38abf {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        // Testa com valor nulo, espera NullPointerException
        try {
            map.containsValue(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // esperado
        }
        // Testa com valor não presente, espera false
        boolean result = map.containsValue("valor_inexistente");
        if (result != false) {
            throw new AssertionError("Expected false for absent value");
        }
        // Testa com valor presente
        map.put("chave1", "valor1");
        if (!map.containsValue("valor1")) {
            throw new AssertionError("Expected true for present value");
        }
        // Testa com valor diferente
        if (map.containsValue("valor2")) {
            throw new AssertionError("Expected false for non-matching value");
        }
    }
}
