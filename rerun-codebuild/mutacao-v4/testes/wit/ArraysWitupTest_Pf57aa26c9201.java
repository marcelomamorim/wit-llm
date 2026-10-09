/* @test
   @summary Testa o método Arrays.copyOfRange para verificar comportamento com índices inválidos.
   @run main ArraysWitupTest_Pf57aa26c9201
 */
import java.util.Arrays;
public class ArraysWitupTest_Pf57aa26c9201 {
    public static void main(String[] args) {
        testCopyOfRangeWithInvalidIndices();
    }
    private static void testCopyOfRangeWithInvalidIndices() {
        short[] original = {1, 2, 3, 4, 5};
        int from = 2;
        int to = 1; // to menor que from, deve lançar IllegalArgumentException
        try {
            Arrays.copyOfRange(original, from, to);
            throw new AssertionError("Expected IllegalArgumentException for to < from");
        } catch (IllegalArgumentException e) {
            // esperado
        }
        from = 4;
        to = 4; // to igual a from, deve retornar array vazio
        short[] result = Arrays.copyOfRange(original, from, to);
        if (result.length != 0) {
            throw new AssertionError("Expected empty array for from == to");
        }
        from = 0;
        to = 10; // to maior que original.length, deve copiar até o fim
        result = Arrays.copyOfRange(original, from, to);
        if (result.length != to - from) {
            throw new AssertionError("Unexpected length for to > original.length");
        }
        if (result[result.length - 1] != original[original.length - 1]) {
            throw new AssertionError("Last element mismatch");
        }
    }
}
