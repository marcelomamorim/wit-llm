/*
 * @test
 * @summary Test for java.lang.Character.codePointCount(char[], int, int)
 * @run main CharacterWitupTest_Pb82d3c78cfe6
 */

import java.lang.Character;

public class CharacterWitupTest_Pb82d3c78cfe6 {

    public static void main(String[] args) {
        testCodePointCount_NormalCase();
        testCodePointCount_OffsetNegative();
        testCodePointCount_CountNegative();
        testCodePointCount_ExceedsArray();
    }

    static void testCodePointCount_NormalCase() {
        char[] array = {'a', 'b', 'c'};
        int offset = 0;
        int count = 3;
        int result = Character.codePointCount(array, offset, count);
        if (result != 3) {
            throw new AssertionError("Expected 3, got " + result);
        }
    }

    static void testCodePointCount_OffsetNegative() {
        char[] array = {'a', 'b', 'c'};
        int offset = -1;
        int count = 2;
        try {
            Character.codePointCount(array, offset, count);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
    }

    static void testCodePointCount_CountNegative() {
        char[] array = {'a', 'b', 'c'};
        int offset = 0;
        int count = -1;
        try {
            Character.codePointCount(array, offset, count);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative count");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
    }

    static void testCodePointCount_ExceedsArray() {
        char[] array = {'a', 'b'};
        int offset = 1;
        int count = 2;
        try {
            Character.codePointCount(array, offset, count);
            throw new AssertionError("Expected IndexOutOfBoundsException for exceeding array bounds");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
    }
}
