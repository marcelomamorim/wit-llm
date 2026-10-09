/*
 * @test
 * @summary Test java.lang.Character.getName(int) method behavior
 * @run main CharacterWitupTest_P5cd2f1306065
 */

public class CharacterWitupTest_P5cd2f1306065 {
    public static void main(String[] args) {
        testValidCodePointWithName();
        testValidCodePointUnassigned();
        testValidCodePointWithBlock();
        testInvalidCodePointThrows();
    }

    private static void testValidCodePointWithName() {
        // Use a known code point with a defined name: 'A' (U+0041)
        String name = java.lang.Character.getName(0x0041);
        if (name == null || !name.equals("LATIN CAPITAL LETTER A")) {
            throw new Error("Expected name 'LATIN CAPITAL LETTER A' for code point 0x0041, got: " + name);
        }
    }

    private static void testValidCodePointUnassigned() {
        // Use a code point that is unassigned (e.g., a high private use area code point)
        // U+E0000 is in Plane 14, but since getName only accepts valid BMP or supplementary code points,
        // we pick a code point that is valid but unassigned in BMP: 0x0378 (unassigned in Unicode 10)
        int unassignedCodePoint = 0x0378;
        String name = java.lang.Character.getName(unassignedCodePoint);
        if (name != null) {
            throw new Error("Expected null for unassigned code point 0x0378, got: " + name);
        }
    }

    private static void testValidCodePointWithBlock() {
        // Use a code point that is assigned but has no name, so block name + hex is returned
        // For example, a private use area code point: 0xE000
        int privateUseCodePoint = 0xE000;
        String name = java.lang.Character.getName(privateUseCodePoint);
        if (name == null) {
            throw new Error("Expected non-null name for private use code point 0xE000");
        }
        // The name should contain the block name with spaces and the uppercase hex code
        if (!name.contains("PRIVATE USE AREA") || !name.contains("E000")) {
            throw new Error("Expected block name and hex for code point 0xE000, got: " + name);
        }
    }

    private static void testInvalidCodePointThrows() {
        // Use an invalid code point (negative)
        try {
            java.lang.Character.getName(-1);
            throw new Error("Expected IllegalArgumentException for code point -1");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Use an invalid code point (greater than max Unicode code point 0x10FFFF)
        try {
            java.lang.Character.getName(0x110000);
            throw new Error("Expected IllegalArgumentException for code point 0x110000");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
