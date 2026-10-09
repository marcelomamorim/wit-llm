/*
 * @test
 * @summary Deterministic tests for java.lang.Character.codePointCount(char[], int, int)
 * @run main CharacterWitupTest_Pc193da5fa39a
 */

public class CharacterWitupTest_Pc193da5fa39a {
    public static void main(String[] args) {
        testValidInput();
        testOffsetNegative();
        testCountNegative();
        testCountPlusOffsetExceedsLength();
    }

    private static void testValidInput() {
        char[] chars = {'a', 'b', 'c', 'd', 'e'};
        int result = java.lang.Character.codePointCount(chars, 1, 3);
        if (result < 0 || result > 3) {
            throw new RuntimeException("Unexpected codePointCount result: " + result);
        }
    }

    private static void testOffsetNegative() {
        char[] chars = {'a', 'b', 'c'};
        try {
            java.lang.Character.codePointCount(chars, -1, 2);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    private static void testCountNegative() {
        char[] chars = {'a', 'b', 'c'};
        try {
            java.lang.Character.codePointCount(chars, 0, -1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative count");
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    private static void testCountPlusOffsetExceedsLength() {
        char[] chars = {'a', 'b', 'c'};
        try {
            java.lang.Character.codePointCount(chars, 2, 2);
            throw new RuntimeException("Expected IndexOutOfBoundsException for count + offset > array length");
        } catch (IndexOutOfBoundsException expected) {
        }
    }
}
