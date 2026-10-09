/* @test
   @summary Testa o método codePointCount com entradas válidas e inválidas.
   @run main CharacterWitupTest_P934f9d1fb2ba
 */
import java.lang.Character;
public class CharacterWitupTest_P934f9d1fb2ba {
    public static void main(String[] args) {
        testValidInputs();
        testInvalidInputs();
    }
    static void testValidInputs() {
        char[] array = {'a', 'b', 'c'};
        int offset = 0;
        int count = 3;
        int result = Character.codePointCount(array, offset, count);
        if (result != 3) {
            throw new AssertionError("Expected 3, got " + result);
        }
        // Test com offset e count menores
        result = Character.codePointCount(array, 1, 2);
        if (result != 2) {
            throw new AssertionError("Expected 2, got " + result);
        }
    }
    static void testInvalidInputs() {
        char[] array = {'a', 'b', 'c'};
        // Test com offset negativo
        try {
            Character.codePointCount(array, -1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // esperado
        }
        // Test com count negativo
        try {
            Character.codePointCount(array, 0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative count");
        } catch (IndexOutOfBoundsException e) {
            // esperado
        }
        // Test com offset + count maior que array.length
        try {
            Character.codePointCount(array, 2, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for offset + count > array.length");
        } catch (IndexOutOfBoundsException e) {
            // esperado
        }
    }
}
