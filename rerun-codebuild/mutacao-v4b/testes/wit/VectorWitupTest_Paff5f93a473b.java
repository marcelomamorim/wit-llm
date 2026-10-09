/* @test
   @summary Testa o método get de Vector para casos de índices válidos e inválidos.
   @run main VectorWitupTest_Paff5f93a473b
 */
import java.util.Vector;
public class VectorWitupTest_Paff5f93a473b {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        
        // Teste de índice válido
        String value = vector.get(1);
        if (!"b".equals(value)) {
            throw new AssertionError("Expected 'b', got: " + value);
        }
        
        // Teste de índice inválido (fora do limite superior)
        try {
            vector.get(3);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index 3");
        } catch (ArrayIndexOutOfBoundsException e) {
            // esperado
        }
        
        // Teste de índice inválido (negativo)
        try {
            vector.get(-1);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index -1");
        } catch (ArrayIndexOutOfBoundsException e) {
            // esperado
        }
    }
}
