/* @test    @summary Test Character.getName(int) for valid and invalid code points    @run main CharacterWitupTest_Pe26da0aff5d8 */

import java.lang.Character;
import java.util.Locale;

public class CharacterWitupTest_Pe26da0aff5d8 {

    public static void main(String[] args) {
        testValidCodePoint();
        testInvalidCodePoint();
        testCodePointWithName();
        testCodePointWithoutName();
    }

    static void testValidCodePoint() {
        int codePoint = 0x41; // 'A'
        String name = Character.getName(codePoint);
        if (name == null || !name.contains("A")) {
            throw new AssertionError("Expected name for code point 0x41, but got: " + name);
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

    static void testCodePointWithName() {
        int codePoint = 0x1F600; // 😀
        String name = Character.getName(codePoint);
        if (name == null || !name.contains("1F600")) {
            throw new AssertionError("Expected name containing '1F600' for code point 0x1F600, but got: " + name);
        }
    }

    static void testCodePointWithoutName() {
        int codePoint = 0x3700; // a CJK Unified Ideograph
        String name = Character.getName(codePoint);
        if (name != null && name.contains("3700")) {
            // name may be null or contain hex string
            return;
        }
        if (name != null) {
            throw new AssertionError("Expected null or name not containing '3700', but got: " + name);
        }
    }
}
