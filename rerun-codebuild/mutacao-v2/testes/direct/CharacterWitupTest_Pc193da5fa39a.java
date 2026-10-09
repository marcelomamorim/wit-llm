/*
 * @test
 * @summary Test java.lang.Character.codePointCount(char[], int, int) for correctness and exceptions
 * @run main CharacterWitupTest_Pc193da5fa39a
 */

public class CharacterWitupTest_Pc193da5fa39a {
    public static void main(String[] args) {
        testValidInputs();
        testInvalidInputs();
    }

    private static void testValidInputs() {
        // Simple BMP characters only
        char[] arr1 = {'a', 'b', 'c', 'd', 'e'};
        int count1 = java.lang.Character.codePointCount(arr1, 0, arr1.length);
        if (count1 != 5) {
            throw new RuntimeException("Expected 5 code points, got " + count1);
        }

        // Include surrogate pair representing one supplementary character
        char high = '\uD83D'; // high surrogate
        char low = '\uDE00';  // low surrogate
        char[] arr2 = {'x', high, low, 'y'};
        // codePointCount should count surrogate pair as one code point
        int count2 = java.lang.Character.codePointCount(arr2, 0, arr2.length);
        if (count2 != 3) {
            throw new RuntimeException("Expected 3 code points, got " + count2);
        }

        // Partial array with offset and count
        int count3 = java.lang.Character.codePointCount(arr1, 1, 3); // 'b','c','d'
        if (count3 != 3) {
            throw new RuntimeException("Expected 3 code points, got " + count3);
        }

        // Single character
        int count4 = java.lang.Character.codePointCount(arr1, 2, 1); // 'c'
        if (count4 != 1) {
            throw new RuntimeException("Expected 1 code point, got " + count4);
        }
    }

    private static void testInvalidInputs() {
        char[] arr = {'a', 'b', 'c'};

        // offset < 0
        try {
            java.lang.Character.codePointCount(arr, -1, 1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for offset < 0");
        } catch (IndexOutOfBoundsException expected) {
        }

        // count < 0
        try {
            java.lang.Character.codePointCount(arr, 0, -1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for count < 0");
        } catch (IndexOutOfBoundsException expected) {
        }

        // count > a.length - offset
        try {
            java.lang.Character.codePointCount(arr, 1, 3); // arr.length=3, 3 > 3-1=2
            throw new RuntimeException("Expected IndexOutOfBoundsException for count > a.length - offset");
        } catch (IndexOutOfBoundsException expected) {
        }

        // offset + count exactly equals length (valid)
        try {
            int result = java.lang.Character.codePointCount(arr, 0, 3);
            if (result != 3) {
                throw new RuntimeException("Expected 3 code points, got " + result);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("Did not expect exception when offset + count == array length");
        }
    }
}
