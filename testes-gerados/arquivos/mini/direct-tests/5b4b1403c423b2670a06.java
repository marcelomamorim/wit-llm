/*
 * @test
 * @summary Test Character.of(int) method for valid and invalid code points
 * @run main CharacterWitupTest
 */

public class CharacterWitupTest {
    public static void main(String[] args) {
        testValidCodePoint();
        testInvalidCodePointNegative();
        testInvalidCodePointTooLarge();
    }

    private static void testValidCodePoint() {
        // Test a valid BMP code point
        java.lang.Character.UnicodeBlock block = java.lang.Character.of(0x0041); // 'A'
        if (block == null) {
            throw new RuntimeException("UnicodeBlock.of returned null for valid code point 0x0041");
        }

        // Test a valid supplementary code point
        block = java.lang.Character.of(0x1F600); // 😀
        if (block == null) {
            throw new RuntimeException("UnicodeBlock.of returned null for valid code point 0x1F600");
        }
    }

    private static void testInvalidCodePointNegative() {
        try {
            java.lang.Character.of(-1);
            throw new RuntimeException("Expected IllegalArgumentException for code point -1");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (!msg.contains("Not a valid Unicode code point")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testInvalidCodePointTooLarge() {
        try {
            java.lang.Character.of(0x110000); // Above Unicode max U+10FFFF
            throw new RuntimeException("Expected IllegalArgumentException for code point 0x110000");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (!msg.contains("Not a valid Unicode code point")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
