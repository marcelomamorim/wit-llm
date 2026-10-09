/*
 * @test
 * @summary Test for Character.codePointCount(char[], int, int)
 * @run main CharacterWitupTest_P977c7112c467
 */

import java.lang.Character;

public class CharacterWitupTest_P977c7112c467 {

    public static void main(String[] args) {
        testCodePointCountValid();
        testCodePointCountIndexOutOfBounds();
        testCodePointCountNegativeOffset();
        testCodePointCountNegativeCount();
    }

    private static void testCodePointCountValid() {
        char[] array = {'a', 'b', 'c', 'd'};
        int offset = 1;
        int count = 2;
        int result = Character.codePointCount(array, offset, count);
        if (result != count) {
            throw new AssertionError("Expected " + count + " but got " + result);
        }
    }

    private static void testCodePointCountIndexOutOfBounds() {
        char[] array = {'a', 'b', 'c'};
        int offset = 2;
        int count = 2; // offset + count = 4 > array.length
        try {
            Character.codePointCount(array, offset, count);
            throw new AssertionError("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCodePointCountNegativeOffset() {
        char[] array = {'a', 'b', 'c'};
        int offset = -1;
        int count = 2;
        try {
            Character.codePointCount(array, offset, count);
            throw new AssertionError("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCodePointCountNegativeCount() {
        char[] array = {'a', 'b', 'c'};
        int offset = 0;
        int count = -1;
        try {
            Character.codePointCount(array, offset, count);
            throw new AssertionError("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
