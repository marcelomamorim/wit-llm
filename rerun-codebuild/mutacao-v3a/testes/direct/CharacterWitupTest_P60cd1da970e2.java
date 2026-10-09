/* @test    @summary Test Character.getName() method for various Unicode code points.    @run main CharacterWitupTest_P60cd1da970e2 */

import java.lang.Character;
import java.util.Locale;

public class CharacterWitupTest_P60cd1da970e2 {

    public static void main(String[] args) {
        testValidCodePoint();
        testUnassignedCodePoint();
        testInvalidCodePointNegative();
        testInvalidCodePointTooLarge();
        testCodePointInBlockName();
    }

    static void testValidCodePoint() {
        int codePoint = 0x41; // 'A'
        String name = Character.getName(codePoint);
        assert name != null : "Expected non-null name for code point 0x41";
        System.out.println("Code point 0x41 name: " + name);
    }

    static void testUnassignedCodePoint() {
        int codePoint = 0x3700; // presumed unassigned, depending on Unicode version
        String name = Character.getName(codePoint);
        assert name == null || !name.isEmpty() : "Expected null or empty name for unassigned code point 0x3700";
        System.out.println("Code point 0x3700 name: " + name);
    }

    static void testInvalidCodePointNegative() {
        int codePoint = -1;
        try {
            Character.getName(codePoint);
            assert false : "Expected IllegalArgumentException for negative code point";
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception for code point -1: " + e.getMessage());
        }
    }

    static void testInvalidCodePointTooLarge() {
        int codePoint = 0x110000; // one more than max valid code point
        try {
            Character.getName(codePoint);
            assert false : "Expected IllegalArgumentException for code point > U+10FFFF";
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception for code point 0x110000: " + e.getMessage());
        }
    }

    static void testCodePointInBlockName() {
        int codePoint = 0x1F600; // 😀
        String name = Character.getName(codePoint);
        assert name != null : "Expected name for code point 0x1F600";
        System.out.println("Code point 0x1F600 name: " + name);
    }
}
