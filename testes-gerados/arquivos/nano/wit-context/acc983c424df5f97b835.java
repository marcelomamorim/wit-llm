/* @test    @summary Test for Character.codePointBefore with various inputs    @run main CharacterWitupTest */

import java.lang.Character;

public class CharacterWitupTest {

    public static void main(String[] args) {
        testCodePointBeforeValid();
        testCodePointBeforeIndexOutOfBounds();
        testCodePointBeforeStartNegative();
        testCodePointBeforeStartExceedsLength();
    }

    private static void testCodePointBeforeValid() {
        char[] array = {'a', 'b', 'c'};
        int index = 2;
        int start = 0;
        int result = Character.codePointBefore(array, index, start);
        if (result != array[start]) {
            throw new AssertionError("Expected code point: " + array[start] + ", but got: " + result);
        }
    }

    private static void testCodePointBeforeIndexOutOfBounds() {
        char[] array = {'a', 'b', 'c'};
        int index = 3;
        int start = 0;
        try {
            Character.codePointBefore(array, index, start);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testCodePointBeforeStartNegative() {
        char[] array = {'a', 'b', 'c'};
        int index = 1;
        int start = -1;
        try {
            Character.codePointBefore(array, index, start);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testCodePointBeforeStartExceedsLength() {
        char[] array = {'a', 'b', 'c'};
        int index = 1;
        int start = 3;
        try {
            Character.codePointBefore(array, index, start);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
