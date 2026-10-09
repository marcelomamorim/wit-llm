/* @test    @summary Test Character.codePointCount for various valid and invalid inputs    @run main CharacterWitupTest_Pb82d3c78cfe6 */

import java.lang.Character;

public class CharacterWitupTest_Pb82d3c78cfe6 {

    public static void main(String[] args) {
        testValidInput();
        testBoundaryConditions();
        testInvalidInput();
    }

    static void testValidInput() {
        char[] array = {'A', '\uD83D', '\uDC36'}; // 'A' and dog face emoji
        int count = Character.codePointCount(array, 0, array.length);
        System.out.println("Valid input: codePointCount = " + count); // Expect 3
    }

    static void testBoundaryConditions() {
        // Test with offset and count within bounds
        char[] array = {'a', 'b', 'c', 'd'};
        int count = Character.codePointCount(array, 1, 2); // from index 1, length 2 ('b', 'c')
        System.out.println("Boundary test: codePointCount = " + count); // Expect 2
    }

    static void testInvalidInput() {
        char[] array = {'x', 'y'};
        try {
            // count exceeds array length
            Character.codePointCount(array, 0, 3);
            System.out.println("Error: Exception not thrown for count > array length");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught expected IndexOutOfBoundsException for count > array length");
        }
        try {
            // negative offset
            Character.codePointCount(array, -1, 2);
            System.out.println("Error: Exception not thrown for negative offset");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught expected IndexOutOfBoundsException for negative offset");
        }
        try {
            // negative count
            Character.codePointCount(array, 0, -1);
            System.out.println("Error: Exception not thrown for negative count");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught expected IndexOutOfBoundsException for negative count");
        }
    }
}
