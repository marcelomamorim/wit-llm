/* @test @summary Test Character.codePointAt with various char arrays and indices @run main CharacterWitupTest_Pa8770cdb6690 */

import java.lang.Character;

public class CharacterWitupTest_Pa8770cdb6690 {
    public static void main(String[] args) {
        testCodePointAtBasic();
        testCodePointAtEdgeCases();
        testCodePointAtInvalidIndices();
    }

    static void testCodePointAtBasic() {
        char[] array = {'A', '\uD83D', '\uDE00', 'Z'}; // 'A', 😀, 'Z'
        // codePointAt at index 0: 'A' (U+0041)
        int cp1 = Character.codePointAt(array, 0, array.length);
        assert cp1 == 0x0041 : "Expected 0x0041, got " + Integer.toHexString(cp1);
        // codePointAt at index 1: high surrogate, should combine with next
        int cp2 = Character.codePointAt(array, 1, array.length);
        assert cp2 == 0x1F600 : "Expected 0x1F600, got " + Integer.toHexString(cp2);
        // codePointAt at index 2: 'Z' (U+005A)
        int cp3 = Character.codePointAt(array, 2, array.length);
        assert cp3 == 0x005A : "Expected 0x005A, got " + Integer.toHexString(cp3);
    }

    static void testCodePointAtEdgeCases() {
        char[] array = {'a', '\uD83D', '\uDE00'};
        // index at last element: surrogate pair
        int cp = Character.codePointAt(array, 1, array.length);
        assert cp == 0x1F600 : "Expected 0x1F600, got " + Integer.toHexString(cp);
    }

    static void testCodePointAtInvalidIndices() {
        char[] array = {'x', 'y'};
        boolean exceptionThrown = false;
        try {
            Character.codePointAt(array, array.length, array.length);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IndexOutOfBoundsException for index >= limit";
        exceptionThrown = false;
        try {
            Character.codePointAt(array, -1, array.length);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IndexOutOfBoundsException for negative index";
        exceptionThrown = false;
        try {
            Character.codePointAt(array, 0, -1);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IndexOutOfBoundsException for negative limit";
    }
}
