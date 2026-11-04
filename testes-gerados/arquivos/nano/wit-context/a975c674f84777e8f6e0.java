/* @test    @summary Test for Character.getName(int) method to verify behavior with valid and invalid code points    @run main CharacterWitupTest */

import java.lang.Character;
import java.util.Locale;

public class CharacterWitupTest {

    public static void main(String[] args) {
        testValidCodePoint();
        testInvalidCodePointNegative();
        testInvalidCodePointTooLarge();
        testUnassignedCodePoint();
        testBlockCodePoint();
    }

    static void testValidCodePoint() {
        int codePoint = 0x41; // 'A'
        String name = Character.getName(codePoint);
        if (name == null || !name.contains("A")) {
            throw new AssertionError("Expected name to contain 'A' for code point 0x41, but got: " + name);
        }
    }

    static void testInvalidCodePointNegative() {
        int invalidCodePoint = -1;
        try {
            Character.getName(invalidCodePoint);
            throw new AssertionError("Expected IllegalArgumentException for code point -1");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidCodePointTooLarge() {
        int invalidCodePoint = 0x110000; // one beyond Unicode max
        try {
            Character.getName(invalidCodePoint);
            throw new AssertionError("Expected IllegalArgumentException for code point 0x110000");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testUnassignedCodePoint() {
        int unassignedCodePoint = 0x0378; // unassigned in Unicode
        String name = Character.getName(unassignedCodePoint);
        if (name != null) {
            throw new AssertionError("Expected null for unassigned code point 0x0378, but got: " + name);
        }
    }

    static void testBlockCodePoint() {
        int codePoint = 0x1F600; // 😀 (Emoji, in Supplementary Multilingual Plane)
        String name = Character.getName(codePoint);
        if (name == null || !name.toLowerCase(Locale.ROOT).contains("1f600")) {
            throw new AssertionError("Expected name to contain '1F600' for code point 0x1F600, but got: " + name);
        }
    }
}
