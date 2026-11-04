/*
 * @test
 * @summary Test Character.codePointBefore(char[], int, int) for boundary and exception cases
 * @run main CharacterWitupTest
 */

public class CharacterWitupTest {
    public static void main(String[] args) {
        testCodePointBeforeValid();
        testCodePointBeforeIndexOutOfBounds();
    }

    private static void testCodePointBeforeValid() {
        char[] chars = {'a', '\uD834', '\uDD1E', 'b'}; // includes a surrogate pair
        int start = 4; // length of array
        int index = 4;
        // Should return code point before index 4, which is the surrogate pair at positions 1 and 2
        int cp = java.lang.Character.codePointBefore(chars, index, start);
        // The code point represented by the surrogate pair is U+1D11E (MUSICAL SYMBOL G CLEF)
        if (cp != 0x1D11E) {
            throw new AssertionError("Expected code point 0x1D11E but got " + Integer.toHexString(cp));
        }
    }

    private static void testCodePointBeforeIndexOutOfBounds() {
        char[] chars = {'a', 'b', 'c'};
        int start = 0;
        int index = 0;
        boolean thrown = false;
        try {
            java.lang.Character.codePointBefore(chars, index, start);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for index <= start");
        }

        thrown = false;
        try {
            java.lang.Character.codePointBefore(chars, 2, -1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for start < 0");
        }

        thrown = false;
        try {
            java.lang.Character.codePointBefore(chars, 2, 4); // start >= a.length
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for start >= a.length");
        }
    }
}
