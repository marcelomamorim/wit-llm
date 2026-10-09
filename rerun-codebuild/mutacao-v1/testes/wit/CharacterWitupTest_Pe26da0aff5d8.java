/* @test    @summary Test Character.getName(int) for valid and invalid code points    @run main CharacterWitupTest_Pe26da0aff5d8 */

import java.lang.Character;
import java.util.Locale;

public class CharacterWitupTest_Pe26da0aff5d8 {

    public static void main(String[] args) {
        testValidCodePoint();
        testInvalidCodePoint();
        testBoundaryCodePoints();
    }

    static void testValidCodePoint() {
        int codePoint = 0x41; // 'A'
        String name = null;
        try {
            name = Character.getName(codePoint);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid code point", e);
        }
        if (name == null || !name.equals("LATIN CAPITAL LETTER A")) {
            throw new AssertionError("Expected name 'LATIN CAPITAL LETTER A' for code point 0x41, but got: " + name);
        }
    }

    static void testInvalidCodePoint() {
        int invalidCodePoint = -1;
        try {
            Character.getName(invalidCodePoint);
            throw new AssertionError("Expected IllegalArgumentException for code point -1, but no exception was thrown");
        } catch (IllegalArgumentException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("Not a valid Unicode code point")) {
                throw new AssertionError("Unexpected exception message: " + message);
            }
        } catch (Exception e) {
            throw new AssertionError("Expected IllegalArgumentException, but got: " + e);
        }
    }

    static void testBoundaryCodePoints() {
        int maxValid = 0x10FFFF;
        String name = null;
        try {
            name = Character.getName(maxValid);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for max valid code point", e);
        }
        // The name may be null or a string; just ensure no exception occurs
        if (name != null && !(name instanceof String)) {
            throw new AssertionError("Expected null or String for max code point, but got: " + name);
        }
        int beyondMax = 0x110000;
        try {
            Character.getName(beyondMax);
            throw new AssertionError("Expected IllegalArgumentException for code point beyond U+10FFFF, but no exception was thrown");
        } catch (IllegalArgumentException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("Not a valid Unicode code point")) {
                throw new AssertionError("Unexpected exception message: " + message);
            }
        } catch (Exception e) {
            throw new AssertionError("Expected IllegalArgumentException, but got: " + e);
        }
    }
}
