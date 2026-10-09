/* @test
   @summary Testa o método Character.toChars com entradas inválidas.
   @run main CharacterWitupTest_P7a67495646b5
 */
import java.lang.Character;
public class CharacterWitupTest_P7a67495646b5 {
    public static void main(String[] args) {
        testInvalidCodePoints();
    }
    private static void testInvalidCodePoints() {
        char[] dst = new char[2];
        int dstIndex = 0;
        int[] invalidCodePoints = { -1, 0x110000, 0xFFFFFF }; // valores fora do intervalo válido
        for (int codePoint : invalidCodePoints) {
            boolean exceptionThrown = false;
            try {
                Character.toChars(codePoint, dst, dstIndex);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected IllegalArgumentException for codePoint: " + codePoint);
            }
        }
    }
}
