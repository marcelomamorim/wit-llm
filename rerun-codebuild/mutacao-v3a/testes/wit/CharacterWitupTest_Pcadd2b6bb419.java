/* @test    @summary Test for Character.codePointAt with various inputs    @run main CharacterWitupTest_Pcadd2b6bb419 */

import java.lang.Character;

public class CharacterWitupTest_Pcadd2b6bb419 {

    public static void main(String[] args) {
        testCodePointAtValid();
        testCodePointAtIndexOutOfBounds();
        testCodePointAtNegativeIndex();
        testCodePointAtIndexEqualToLimit();
    }

    private static void testCodePointAtValid() {
        char[] array = {'a', 'b', 'c'};
        int index = 1;
        int limit = 3;
        int result = Character.codePointAt(array, index, limit);
        if (result != array[index]) {
            throw new AssertionError("Expected code point: " + (int)array[index] + ", but got: " + result);
        }
    }

    private static void testCodePointAtIndexOutOfBounds() {
        char[] array = {'a', 'b', 'c'};
        int index = 3;
        int limit = 3;
        boolean threw = false;
        try {
            Character.codePointAt(array, index, limit);
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index equal to limit");
        }
    }

    private static void testCodePointAtNegativeIndex() {
        char[] array = {'a', 'b', 'c'};
        int index = -1;
        int limit = 3;
        boolean threw = false;
        try {
            Character.codePointAt(array, index, limit);
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        }
    }

    private static void testCodePointAtIndexEqualToLimit() {
        char[] array = {'a', 'b', 'c'};
        int index = 2;
        int limit = 2;
        boolean threw = false;
        try {
            Character.codePointAt(array, index, limit);
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index equal to limit");
        }
    }
}
