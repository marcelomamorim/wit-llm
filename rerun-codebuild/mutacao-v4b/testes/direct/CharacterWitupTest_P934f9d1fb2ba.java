/* @test
   @summary Testa o método codePointCount com casos válidos e inválidos.
   @run main CharacterWitupTest_P934f9d1fb2ba
 */
import java.lang.Character;
public class CharacterWitupTest_P934f9d1fb2ba {
    public static void main(String[] args) {
        testValidInput();
        testInvalidInput();
    }
    static void testValidInput() {
        char[] array = {'a', '\uD83D', '\uDE00'}; // 'a', emoji grinning face
        int count = Character.codePointCount(array, 0, array.length);
        if (count != 3) {
            throw new AssertionError("Expected 3 code points, got " + count);
        }
        int count2 = Character.codePointCount(array, 1, 2); // only emoji
        if (count2 != 1) {
            throw new AssertionError("Expected 1 code point, got " + count2);
        }
    }
    static void testInvalidInput() {
        char[] array = {'a', 'b'};
        try {
            Character.codePointCount(array, -1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            Character.codePointCount(array, 0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative count");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            Character.codePointCount(array, 2, 1); // offset beyond array
            throw new AssertionError("Expected IndexOutOfBoundsException for offset beyond array length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            Character.codePointCount(array, 0, 3); // count exceeds array length
            throw new AssertionError("Expected IndexOutOfBoundsException for count exceeding array length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
