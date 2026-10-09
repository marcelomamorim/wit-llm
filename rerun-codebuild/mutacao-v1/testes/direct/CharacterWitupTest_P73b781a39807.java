/* @test    @summary Test Character.codePointCount with valid inputs    @run main CharacterWitupTest_P73b781a39807 */

import java.lang.Character;

public class CharacterWitupTest_P73b781a39807 {
    public static void main(String[] args) {
        testCodePointCountBasic();
        testCodePointCountWithSurrogates();
        testCodePointCountWithInvalidBounds();
    }

    static void testCodePointCountBasic() {
        char[] array = {'a', 'b', 'c'};
        int count = Character.codePointCount(array, 0, array.length);
        if (count != 3) {
            throw new AssertionError("Expected 3, got " + count);
        }
    }

    static void testCodePointCountWithSurrogates() {
        // Surrogate pair for U+1D11E (musical symbol G clef)
        char highSurrogate = '\uD834';
        char lowSurrogate = '\uDD1E';
        char[] array = {highSurrogate, lowSurrogate, 'x'};
        int count = Character.codePointCount(array, 0, array.length);
        if (count != 2) {
            throw new AssertionError("Expected 2, got " + count);
        }
    }

    static void testCodePointCountWithInvalidBounds() {
        char[] array = {'a', 'b'};
        try {
            Character.codePointCount(array, -1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            Character.codePointCount(array, 0, 3);
            throw new AssertionError("Expected IndexOutOfBoundsException for count exceeding array length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            Character.codePointCount(array, 1, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative count");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
