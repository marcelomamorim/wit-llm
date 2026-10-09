/* @test
   @summary Verifies Character.toChars method for BMP and supplementary code points.
   @run main CharacterWitupTest_P7a67495646b5
 */
import java.lang.Character;
public class CharacterWitupTest_P7a67495646b5 {
    public static void main(String[] args) {
        testBmpCodePoint();
        testSupplementaryCodePoint();
        testInvalidCodePoint();
    }
    private static void testBmpCodePoint() {
        int codePoint = 0x0041; // 'A'
        char[] dst = new char[2];
        int length = Character.toChars(codePoint, dst, 0);
        assert length == 1 : "Expected length 1 for BMP code point";
        assert dst[0] == (char) codePoint : "Incorrect character for BMP code point";
    }
    private static void testSupplementaryCodePoint() {
        int codePoint = 0x1F600; // 😀
        char[] dst = new char[2];
        int length = Character.toChars(codePoint, dst, 0);
        assert length == 2 : "Expected length 2 for supplementary code point";
        int highSurrogate = dst[0];
        int lowSurrogate = dst[1];
        assert Character.isHighSurrogate((char) highSurrogate) : "High surrogate expected";
        assert Character.isLowSurrogate((char) lowSurrogate) : "Low surrogate expected";
        int reconstructedCodePoint = Character.toCodePoint((char) highSurrogate, (char) lowSurrogate);
        assert reconstructedCodePoint == codePoint : "Reconstructed code point does not match";
    }
    private static void testInvalidCodePoint() {
        int invalidCodePoint = -1;
        char[] dst = new char[2];
        boolean exceptionThrown = false;
        try {
            Character.toChars(invalidCodePoint, dst, 0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for invalid code point";
    }
}
