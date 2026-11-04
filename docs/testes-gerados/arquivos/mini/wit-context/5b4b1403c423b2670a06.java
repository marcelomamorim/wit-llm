/*
 * @test
 * @summary Test Character.of(int) method for valid and invalid Unicode code points
 * @run main CharacterWitupTest
 */

public class CharacterWitupTest {
    public static void main(String[] args) {
        testValidCodePoints();
        testInvalidCodePoints();
        System.out.println("All tests passed.");
    }

    private static void testValidCodePoints() {
        // Test some valid code points within Unicode range
        int[] validPoints = {0x0000, 0x0041, 0x00DF, 0x07FF, 0x0800, 0xD7FF, 0xE000, 0xFFFF, 0x10000, 0x10FFFF};
        for (int cp : validPoints) {
            java.lang.Character.UnicodeBlock block = java.lang.Character.UnicodeBlock.of(cp);
            if (block == null) {
                throw new AssertionError("UnicodeBlock.of returned null for valid code point: 0x" + Integer.toHexString(cp));
            }
        }
    }

    private static void testInvalidCodePoints() {
        // Test code points outside valid Unicode range
        int[] invalidPoints = {-1, 0x110000, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int cp : invalidPoints) {
            try {
                java.lang.Character.UnicodeBlock.of(cp);
                throw new AssertionError("Expected IllegalArgumentException for invalid code point: 0x" + Integer.toHexString(cp));
            } catch (IllegalArgumentException e) {
                // Expected exception
                String msg = e.getMessage();
                if (!msg.contains("Not a valid Unicode code point")) {
                    throw new AssertionError("Unexpected exception message: " + msg);
                }
            }
        }
    }
}
