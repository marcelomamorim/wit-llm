/* @test    @summary Test for Character.codePointAt with boundary conditions    @run main CharacterWitupTest_Pdbc86cfeab6f */

import java.lang.Character;

public class CharacterWitupTest_Pdbc86cfeab6f {

    public static void main(String[] args) {
        testCodePointAtWithInvalidIndices();
    }

    private static void testCodePointAtWithInvalidIndices() {
        char[] array = {'a', 'b', 'c'};
        int length = array.length;
        boolean exceptionThrown = false;
        try {
            // index >= limit
            Character.codePointAt(array, 2, 2);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index >= limit");
        }

        exceptionThrown = false;
        try {
            // limit < 0
            Character.codePointAt(array, 0, -1);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for limit < 0");
        }

        exceptionThrown = false;
        try {
            // limit > array.length
            Character.codePointAt(array, 0, length + 1);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for limit > array.length");
        }
    }
}
