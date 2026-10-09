/* @test
   @summary Verifica comportamento do método Character.getName com entradas válidas e inválidas.
   @run main CharacterWitupTest_P2125497be365
 */
import java.lang.Character;
import java.util.Locale;
public class CharacterWitupTest_P2125497be365 {
    public static void main(String[] args) {
        testValidCodePoints();
        testInvalidCodePoints();
        testUnassignedCodePoint();
        testCodePointInBlock();
    }
    static void testValidCodePoints() {
        // Testa um código válido que possui nome
        int codePoint = 0x0041; // 'A'
        String name = Character.getName(codePoint);
        if (name == null || !name.contains("A")) {
            throw new AssertionError("Failed: getName(0x0041) should contain 'A', got: " + name);
        }
        // Testa um código válido que é um bloco Unicode
        int blockCodePoint = 0x4E00; // CJK Unified Ideograph
        String blockName = Character.getName(blockCodePoint);
        if (blockName == null || !blockName.contains("4E00")) {
            throw new AssertionError("Failed: getName(0x4E00) should contain '4E00', got: " + blockName);
        }
    }
    static void testInvalidCodePoints() {
        // Código inválido (fora do intervalo Unicode)
        int invalidCodePoint = 0x110000; // além de U+10FFFF
        try {
            Character.getName(invalidCodePoint);
            throw new AssertionError("Expected IllegalArgumentException for codePoint=0x" + Integer.toHexString(invalidCodePoint));
        } catch (IllegalArgumentException e) {
            // esperado
        }
        // Código negativo
        int negativeCodePoint = -1;
        try {
            Character.getName(negativeCodePoint);
            throw new AssertionError("Expected IllegalArgumentException for codePoint=-1");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }
    static void testUnassignedCodePoint() {
        // Código de ponto Unicode que é unassigned
        int unassigned = 0x0378; // não atribuído
        String name = Character.getName(unassigned);
        if (name != null) {
            throw new AssertionError("Failed: getName(0x0378) should be null, got: " + name);
        }
    }
    static void testCodePointInBlock() {
        // Código dentro de um bloco Unicode
        int codePoint = 0x1F600; // Emoji 😀
        String name = Character.getName(codePoint);
        if (name != null && !name.contains("1F600")) {
            throw new AssertionError("Failed: getName(0x1F600) should contain '1F600', got: " + name);
        }
    }
}
