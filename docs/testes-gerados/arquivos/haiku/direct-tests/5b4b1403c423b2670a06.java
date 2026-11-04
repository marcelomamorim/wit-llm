/*
 * @test
 * @summary Verifies Character.UnicodeBlock.of(int) boundary blocks and invalid code point handling
 * @run main CharacterWitupTest
 */

public class CharacterWitupTest {
    public static void main(String[] args) {
        check(Character.UnicodeBlock.of(0) == Character.UnicodeBlock.BASIC_LATIN, "U+0000 should be BASIC_LATIN");
        check(Character.UnicodeBlock.of(0x0041) == Character.UnicodeBlock.BASIC_LATIN, "U+0041 should be BASIC_LATIN");
        check(Character.UnicodeBlock.of(0x007F) == Character.UnicodeBlock.BASIC_LATIN, "U+007F should be BASIC_LATIN");
        check(Character.UnicodeBlock.of(0x0080) == Character.UnicodeBlock.LATIN_1_SUPPLEMENT, "U+0080 should be LATIN_1_SUPPLEMENT");
        check(Character.UnicodeBlock.of(0x0100) == Character.UnicodeBlock.LATIN_EXTENDED_A, "U+0100 should be LATIN_EXTENDED_A");
        check(Character.UnicodeBlock.of(0x0300) == Character.UnicodeBlock.COMBINING_DIACRITICAL_MARKS, "U+0300 should be COMBINING_DIACRITICAL_MARKS");
        check(Character.UnicodeBlock.of(0x0391) == Character.UnicodeBlock.GREEK, "U+0391 should be GREEK");
        check(Character.UnicodeBlock.of(0x10FFFF) == Character.UnicodeBlock.SUPPLEMENTARY_PRIVATE_USE_AREA_B, "U+10FFFF should be SUPPLEMENTARY_PRIVATE_USE_AREA_B");
        check(Character.UnicodeBlock.of(0xD800) != null, "surrogate code point should map to a block");

        checkInvalid(-1);
        checkInvalid(0x110000);

        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkInvalid(int codePoint) {
        try {
            Character.UnicodeBlock.of(codePoint);
        } catch (IllegalArgumentException e) {
            check(e.getMessage() != null
                    && e.getMessage().startsWith("Not a valid Unicode code point"),
                    "unexpected message for " + codePoint + ": " + e.getMessage());
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for " + codePoint);
    }
}
