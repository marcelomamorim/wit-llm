/*
 * @test
 * @summary Test Character.codePointAt(char[] a, int index, int limit) for bounds checking and normal behavior
 * @run main CharacterWitupTest_P8ff7c3cb5655
 */

public class CharacterWitupTest_P8ff7c3cb5655 {
    public static void main(String[] args) {
        testIndexOutOfBounds();
        testValidCodePointAt();
    }

    private static void testIndexOutOfBounds() {
        char[] array = { 'a', 'b', 'c' };
        // index >= limit
        try {
            Character.codePointAt(array, 2, 2);
            throw new RuntimeException("Expected IndexOutOfBoundsException for index >= limit");
        } catch (IndexOutOfBoundsException expected) {
        }

        // limit < 0
        try {
            Character.codePointAt(array, 0, -1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for limit < 0");
        } catch (IndexOutOfBoundsException expected) {
        }

        // limit > array.length
        try {
            Character.codePointAt(array, 0, 4);
            throw new RuntimeException("Expected IndexOutOfBoundsException for limit > array.length");
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    private static void testValidCodePointAt() {
        char[] array = { 'a', '\uD83D', '\uDE00', 'z' };
        // 'a' at index 0
        int cp0 = Character.codePointAt(array, 0, array.length);
        if (cp0 != 'a') {
            throw new RuntimeException("Expected code point 'a' at index 0, got: " + cp0);
        }

        // surrogate pair at index 1 (U+1F600 GRINNING FACE)
        int cp1 = Character.codePointAt(array, 1, array.length);
        if (cp1 != 0x1F600) {
            throw new RuntimeException("Expected code point U+1F600 at index 1, got: " + Integer.toHexString(cp1));
        }

        // 'z' at index 3
        int cp3 = Character.codePointAt(array, 3, array.length);
        if (cp3 != 'z') {
            throw new RuntimeException("Expected code point 'z' at index 3, got: " + cp3);
        }
    }
}
