/* @test
   @summary Testa o método removeElementAt de java.util.Vector.
   @run main VectorWitupTest_P798ea7a36127
 */
import java.util.Vector;
public class VectorWitupTest_P798ea7a36127 {
    public static void main(String[] args) {
        testRemoveElementAt();
    }
    static void testRemoveElementAt() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        // Remove elemento válido
        vector.removeElementAt(1);
        assert vector.size() == 2 : "Tamanho esperado 2";
        assert vector.get(0) == 1 : "Primeiro elemento deve ser 1";
        assert vector.get(1) == 3 : "Segundo elemento deve ser 3";
        // Testa remoção com índice igual ao tamanho (fora do limite)
        try {
            vector.removeElementAt(vector.size());
            throw new AssertionError("Esperado ArrayIndexOutOfBoundsException")
        } catch (ArrayIndexOutOfBoundsException e) {
            // esperado
        }
        // Testa remoção com índice negativo
        try {
            vector.removeElementAt(-1);
            throw new AssertionError("Esperado ArrayIndexOutOfBoundsException")
        } catch (ArrayIndexOutOfBoundsException e) {
            // esperado
        }
        // Remove último elemento
        vector.removeElementAt(1);
        assert vector.size() == 1 : "Tamanho esperado 1";
        assert vector.get(0) == 1 : "Elemento restante deve ser 1";
    }
}
