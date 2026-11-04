/* @test    @summary Test Character.codePointBefore method for valid and invalid inputs    @run main CharacterWitupTest */

import java.lang.Character;

public class CharacterWitupTest {

    public static void main(String[] args) {
        testValidCodePointBefore();
        testInvalidIndexTooSmall();
        testInvalidIndexEqualStart();
        testInvalidStartNegative();
        testInvalidStartTooLarge();
    }

    private static void testValidCodePointBefore() {
        char[] array = {'A', 'B', 'C', 'D'};
        int index = 2; // position after 'B'
        int start = 0;
        int result = Character.codePointBefore(array, index, start);
        // Expecting code point of 'B'
        int expected = array[start + 1];
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    private static void testInvalidIndexTooSmall() {
        char[] array = {'A', 'B', 'C'};
        int index = 0;
        int start = 0;
        try {
            Character.codePointBefore(array, index, start);
            throw new AssertionError("Expected IndexOutOfBoundsException for index=0");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInvalidIndexEqualStart() {
        char[] array = {'A', 'B', 'C'};
        int index = 1;
        int start = 1;
        try {
            Character.codePointBefore(array, index, start);
            throw new AssertionError("Expected IndexOutOfBoundsException for index=1, start=1");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInvalidStartNegative() {
        char[] array = {'A', 'B', 'C'};
        int index = 2;
        int start = -1;
        try {
            Character.codePointBefore(array, index, start);
            throw new AssertionError("Expected IndexOutOfBoundsException for start=-1");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInvalidStartTooLarge() {
        char[] array = {'A', 'B', 'C'};
        int index = 2;
        int start = array.length; // 3
        try {
            Character.codePointBefore(array, index, start);
            throw new AssertionError("Expected IndexOutOfBoundsException for start=3");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
