/* @test    @summary Test Character.codePointAt with valid and invalid inputs    @run main CharacterWitupTest_Pdbc86cfeab6f */

import java.lang.Character;

public class CharacterWitupTest_Pdbc86cfeab6f {

    public static void main(String[] args) {
        testCodePointAtValid();
        testCodePointAtIndexOutOfBounds();
        testCodePointAtNegativeIndex();
        testCodePointAtLimitExceedsLength();
    }

    private static void testCodePointAtValid() {
        char[] array = {'A', '\uD83D', '\uDE00'}; // 'A', 😀 (surrogate pair)
        int index = 0;
        int limit = array.length;
        int result = Character.codePointAt(array, index, limit);
        if (result != 65) { // 'A' code point
            throw new AssertionError("Expected 65, got " + result);
        }
        index = 1;
        result = Character.codePointAt(array, index, limit);
        if (result != 0x1F600) { // 😀 code point
            throw new AssertionError("Expected 0x1F600, got " + result);
        }
    }

    private static void testCodePointAtIndexOutOfBounds() {
        char[] array = {'A', 'B'};
        int limit = array.length;
        try {
            Character.codePointAt(array, limit, limit);
            throw new AssertionError("Expected IndexOutOfBoundsException for index == limit");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCodePointAtNegativeIndex() {
        char[] array = {'A', 'B'};
        int limit = array.length;
        try {
            Character.codePointAt(array, -1, limit);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCodePointAtLimitExceedsLength() {
        char[] array = {'A', 'B'};
        int limit = array.length + 1;
        try {
            Character.codePointAt(array, 0, limit);
            throw new AssertionError("Expected IndexOutOfBoundsException for limit > array length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
