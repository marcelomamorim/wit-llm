/*
 * @test
 * @summary Witup: Character.UnicodeBlock.of(int) must reject invalid code points and map valid ones to their blocks
 * @run main CharacterWitupTest
 */

public class CharacterWitupTest {

    public static void main(String[] args) {
        checkBlock(0x0000, Character.UnicodeBlock.BASIC_LATIN);
        checkBlock(0x0041, Character.UnicodeBlock.BASIC_LATIN);
        checkBlock(0x007F, Character.UnicodeBlock.BASIC_LATIN);
        checkBlock(0x00E9, Character.UnicodeBlock.LATIN_1_SUPPLEMENT);
        checkBlock(0x0370, Character.UnicodeBlock.GREEK);
        checkBlock(0x10FFFF, Character.UnicodeBlock.SUPPLEMENTARY_PRIVATE_USE_AREA_B);

        checkIllegalArgument(-1);
        checkIllegalArgument(0x110000);
        checkIllegalArgument(Integer.MAX_VALUE);
        checkIllegalArgument(Integer.MIN_VALUE);
    }

    private static void checkBlock(int codePoint, Character.UnicodeBlock expected) {
        Character.UnicodeBlock actual = Character.UnicodeBlock.of(codePoint);
        if (actual != expected) {
            throw new RuntimeException("Unexpected block for U+" + Integer.toHexString(codePoint)
                    + ": " + actual + ", expected " + expected);
        }
    }

    private static void checkIllegalArgument(int codePoint) {
        try {
            Character.UnicodeBlock.of(codePoint);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException not thrown for code point 0x"
                + Integer.toHexString(codePoint));
    }
}
