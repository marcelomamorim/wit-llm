/* @test    @summary Test Character.codePointCount with valid inputs    @run main CharacterWitupTest_P977c7112c467 */

import java.lang.Character;

public class CharacterWitupTest_P977c7112c467 {

    public static void main(String[] args) {
        testCodePointCountBasic();
        testCodePointCountWithOffset();
        testCodePointCountWithInvalidIndex();
        testCodePointCountWithInvalidCount();
    }

    static void testCodePointCountBasic() {
        char[] array = {'a', 'b', 'c'};
        int count = Character.codePointCount(array, 0, array.length);
        if (count != 3) {
            throw new AssertionError("Expected 3, but got " + count);
        }
    }

    static void testCodePointCountWithOffset() {
        char[] array = {'a', 'b', 'c', 'd'};
        int count = Character.codePointCount(array, 1, 2);
        if (count != 2) {
            throw new AssertionError("Expected 2, but got " + count);
        }
    }

    static void testCodePointCountWithInvalidIndex() {
        char[] array = {'a', 'b'};
        try {
            Character.codePointCount(array, -1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCodePointCountWithInvalidCount() {
        char[] array = {'a', 'b'};
        try {
            Character.codePointCount(array, 0, 3);
            throw new AssertionError("Expected IndexOutOfBoundsException for count exceeding array length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
