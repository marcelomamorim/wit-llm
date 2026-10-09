/* @test    @summary Test Character.getName(int) for valid and invalid code points    @run main CharacterWitupTest_Pebd931cd58d6 */

import java.lang.Character;
import java.util.Locale;

public class CharacterWitupTest_Pebd931cd58d6 {

    public static void main(String[] args) {
        testValidCodePoint();
        testInvalidCodePoint();
        testUnassignedCodePoint();
        testCodePointInBlock();
    }

    static void testValidCodePoint() {
        int codePoint = 0x0041; // 'A'
        String name = Character.getName(codePoint);
        if (name == null || !name.contains("A")) {
            throw new AssertionError("Expected name containing 'A' for code point 0x0041, but got: " + name);
        }
    }

    static void testInvalidCodePoint() {
        int invalidCodePoint = -1;
        try {
            Character.getName(invalidCodePoint);
            throw new AssertionError("Expected IllegalArgumentException for code point -1");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("0x-1")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }

    static void testUnassignedCodePoint() {
        int unassignedCodePoint = 0x0378; // Unassigned in Unicode
        String name = Character.getName(unassignedCodePoint);
        if (name != null) {
            throw new AssertionError("Expected null for unassigned code point 0x0378, but got: " + name);
        }
    }

    static void testCodePointInBlock() {
        int codePoint = 0x1F600; // 😀 (Emoji, in 'Emoticons' block)
        String name = Character.getName(codePoint);
        if (name == null || !name.contains("1F600")) {
            throw new AssertionError("Expected name containing '1F600' for code point 0x1F600, but got: " + name);
        }
    }
}
