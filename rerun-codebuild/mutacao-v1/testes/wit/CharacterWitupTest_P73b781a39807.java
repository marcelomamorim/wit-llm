/* @test    @summary Test for Character.codePointCount(char[], int, int) handling valid and invalid inputs    @run main CharacterWitupTest_P73b781a39807 */

import java.lang.Character;

public class CharacterWitupTest_P73b781a39807 {

    public static void main(String[] args) {
        testValidInputs();
        testNegativeOffset();
        testOffsetExceedsArray();
        testCountExceedsArray();
        testZeroCount();
        System.out.println("All tests passed.");
    }

    static void testValidInputs() {
        char[] array = {'a', 'b', 'c', 'd'};
        int offset = 1;
        int count = 2;
        int result = Character.codePointCount(array, offset, count);
        if (result != 2) {
            throw new AssertionError("Expected 2, got " + result);
        }
    }

    static void testNegativeOffset() {
        char[] array = {'a', 'b', 'c'};
        try {
            Character.codePointCount(array, -1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testOffsetExceedsArray() {
        char[] array = {'a', 'b'};
        try {
            Character.codePointCount(array, 3, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for offset exceeding array length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCountExceedsArray() {
        char[] array = {'a', 'b', 'c'};
        try {
            Character.codePointCount(array, 1, 3);
            throw new AssertionError("Expected IndexOutOfBoundsException for count exceeding array bounds");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testZeroCount() {
        char[] array = {'a', 'b'};
        int result = Character.codePointCount(array, 0, 0);
        if (result != 0) {
            throw new AssertionError("Expected 0 for zero count, got " + result);
        }
    }
}
