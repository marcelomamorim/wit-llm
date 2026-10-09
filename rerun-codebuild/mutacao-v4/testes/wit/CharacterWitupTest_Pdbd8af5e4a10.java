/* @test
   @summary Testa o método codePointAt com entradas inválidas para disparar IndexOutOfBoundsException.
   @run main CharacterWitupTest_Pdbd8af5e4a10
 */
import java.lang.Character;
public class CharacterWitupTest_Pdbd8af5e4a10 {
    public static void main(String[] args) {
        char[] array = {'a', 'b', 'c'};
        // Testa índice negativo
        try {
            Character.codePointAt(array, -1, array.length);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            /* esperado */
        }
        // Testa índice igual a limit
        try {
            Character.codePointAt(array, array.length, array.length);
            throw new AssertionError("Expected IndexOutOfBoundsException for index == limit");
        } catch (IndexOutOfBoundsException e) {
            /* esperado */
        }
        // Testa índice maior que limit
        try {
            Character.codePointAt(array, array.length + 1, array.length);
            throw new AssertionError("Expected IndexOutOfBoundsException for index > limit");
        } catch (IndexOutOfBoundsException e) {
            /* esperado */
        }
        // Testa limit maior que array.length
        try {
            Character.codePointAt(array, 0, array.length + 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for limit > array.length");
        } catch (IndexOutOfBoundsException e) {
            /* esperado */
        }
        // Testa limit negativo
        try {
            Character.codePointAt(array, 0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative limit");
        } catch (IndexOutOfBoundsException e) {
            /* esperado */
        }
    }
}
