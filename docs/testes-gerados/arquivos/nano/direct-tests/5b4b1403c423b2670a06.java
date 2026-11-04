/* @test    @summary Test Character.of(int) method for valid and invalid code points    @run main CharacterWitupTest */

import java.lang.Character;
import java.lang.UnicodeBlock;

public class CharacterWitupTest {

    public static void main(String[] args) {
        testValidCodePoint();
        testInvalidCodePoint();
    }

    static void testValidCodePoint() {
        int[] validCodePoints = {0x0041, 0x1F600, 0x10FFFF}; // 'A', 😀, max Unicode
        for (int cp : validCodePoints) {
            UnicodeBlock block = Character.of(cp);
            if (block == null) {
                throw new AssertionError("Expected non-null UnicodeBlock for code point: " + cp);
            }
        }
    }

    static void testInvalidCodePoint() {
        int[] invalidCodePoints = {-1, 0x110000}; // invalid code points
        for (int cp : invalidCodePoints) {
            try {
                Character.of(cp);
                throw new AssertionError("Expected IllegalArgumentException for code point: " + cp);
            } catch (IllegalArgumentException e) {
                // expected
            }
        }
    }
}
