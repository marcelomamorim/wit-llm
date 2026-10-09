/* @test
   @summary Testa o método contains de Hashtable para verificar comportamento com valores nulos.
   @run main HashtableWitupTest_P2a76941a6755
 */
import java.util.Hashtable;
public class HashtableWitupTest_P2a76941a6755 {
    public static void main(String[] args) {
        Hashtable<String, String> table = new Hashtable<>();
        table.put("key1", "value1");
        table.put("key2", "value2");
        // Testa comportamento com valor não nulo
        boolean containsValue1 = table.contains("value1");
        if (!containsValue1) {
            throw new AssertionError("Expected to contain 'value1'");
        }
        // Testa comportamento com valor nulo, deve lançar NullPointerException
        try {
            table.contains(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            // esperado
        }
    }
}
