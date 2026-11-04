/* @test    @summary Test for Character.of(int) method to verify handling of valid and invalid code points    @run main CharacterWitupTest */

import java.lang.Character;
import java.lang.UnicodeBlock;

public class CharacterWitupTest {

    public static void main(String[] args) {
        testValidCodePoint();
        testInvalidCodePointNegative();
        testInvalidCodePointTooLarge();
    }

    private static void testValidCodePoint() {
        int validCodePoint = 0x41; // 'A'
        UnicodeBlock block = Character.of(validCodePoint);
        if (block == null) {
            throw new AssertionError("Expected non-null UnicodeBlock for valid code point") ;
        }
        System.out.println("testValidCodePoint passed");
    }

    private static void testInvalidCodePointNegative() {
        int invalidCodePoint = -1;
        try {
            Character.of(invalidCodePoint);
            throw new AssertionError("Expected IllegalArgumentException for negative code point") ;
        } catch (IllegalArgumentException e) {
            System.out.println("testInvalidCodePointNegative passed");
        }
    }

    private static void testInvalidCodePointTooLarge() {
        int invalidCodePoint = 0x110000; // one more than U+10FFFF
        try {
            Character.of(invalidCodePoint);
            throw new AssertionError("Expected IllegalArgumentException for code point > U+10FFFF") ;
        } catch (IllegalArgumentException e) {
            System.out.println("testInvalidCodePointTooLarge passed");
        }
    }
}
