/*
 * @test
 * @summary Tests for Character.getName(int)
 * @run main CharacterWitupTest_P60cd1da970e2
 */

import java.lang.Character;
import java.util.Locale;

public class CharacterWitupTest_P60cd1da970e2 {

    public static void main(String[] args) {
        testValidCodePoint();
        testInvalidCodePointNegative();
        testInvalidCodePointTooLarge();
        testBoundaryCodePoint();
        testUnassignedCodePoint();
    }

    static void testValidCodePoint() {
        int codePoint = 65; // 'A'
        String name = Character.getName(codePoint);
        if (name == null || !name.equals("LATIN CAPITAL LETTER A")) {
            throw new AssertionError("Expected name for U+0041, got: " + name);
        }
    }

    static void testInvalidCodePointNegative() {
        int codePoint = -1;
        try {
            Character.getName(codePoint);
            throw new AssertionError("Expected IllegalArgumentException for codePoint -1");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    static void testInvalidCodePointTooLarge() {
        int codePoint = 0x110000; // one above Unicode max
        try {
            Character.getName(codePoint);
            throw new AssertionError("Expected IllegalArgumentException for codePoint 0x110000");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    static void testBoundaryCodePoint() {
        int codePoint = 0x10FFFF; // Max valid code point
        String name = Character.getName(codePoint);
        if (name != null && name.isEmpty()) {
            throw new AssertionError("Expected non-empty name or null for U+10FFFF, got: " + name);
        }
    }

    static void testUnassignedCodePoint() {
        int codePoint = 0x0378; // Unassigned in Unicode 10.0.0
        String name = Character.getName(codePoint);
        if (name != null && !name.isEmpty()) {
            throw new AssertionError("Expected null or empty for unassigned U+0378, got: " + name);
        }
    }
}
