/*
 * @test
 * @summary Test Character.getName(int) for valid, invalid, and edge Unicode code points
 * @run main CharacterWitupTest
 */

public class CharacterWitupTest {
    public static void main(String[] args) {
        testValidCodePoint();
        testInvalidCodePoint();
        testUnassignedCodePoint();
        testUnicodeBlockNameFormat();
    }

    private static void testValidCodePoint() {
        // Test a known valid code point with a defined name
        String name = java.lang.Character.getName('A'); // U+0041 LATIN CAPITAL LETTER A
        if (name == null || !name.contains("LATIN")) {
            throw new AssertionError("Expected name containing 'LATIN' for code point 'A', got: " + name);
        }
    }

    private static void testInvalidCodePoint() {
        // Test an invalid code point (greater than Unicode max U+10FFFF)
        int invalidCodePoint = 0x110000; // One above max
        try {
            java.lang.Character.getName(invalidCodePoint);
            throw new AssertionError("Expected IllegalArgumentException for invalid code point: " + invalidCodePoint);
        } catch (IllegalArgumentException e) {
            // Expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Not a valid Unicode code point")) {
                throw new AssertionError("Exception message unexpected: " + msg);
            }
        }
    }

    private static void testUnassignedCodePoint() {
        // Test a valid but unassigned code point returns null
        // Use a code point in a range known to be unassigned, e.g. 0x2FFFF (Plane 2, unassigned)
        int unassigned = 0x2FFFF;
        // Only test if code point is valid
        if (java.lang.Character.isValidCodePoint(unassigned)) {
            String name = java.lang.Character.getName(unassigned);
            if (name != null) {
                throw new AssertionError("Expected null for unassigned code point, got: " + name);
            }
        }
    }

    private static void testUnicodeBlockNameFormat() {
        // Test a code point with no name but with a UnicodeBlock
        // For example, a private use area code point
        int privateUse = 0xE000; // Start of BMP private use area
        if (java.lang.Character.isValidCodePoint(privateUse)) {
            String name = java.lang.Character.getName(privateUse);
            if (name == null) {
                throw new AssertionError("Expected non-null name for private use code point");
            }
            // The name should contain the block name with spaces and the hex code point
            if (!name.contains("Private Use") || !name.toUpperCase().contains(Integer.toHexString(privateUse).toUpperCase())) {
                throw new AssertionError("Name format unexpected for private use code point: " + name);
            }
        }
    }
}
