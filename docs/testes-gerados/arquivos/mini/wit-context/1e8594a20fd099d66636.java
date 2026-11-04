/*
 * @test
 * @summary Test StringLatin1.charAt(byte[], int) for valid and invalid indices
 * @run main java.lang.StringLatin1WitupTest
 */

public class StringLatin1WitupTest {
    public static void main(String[] args) {
        testCharAtValidIndex();
        testCharAtInvalidIndexNegative();
        testCharAtInvalidIndexTooLarge();
        System.out.println("All tests passed.");
    }

    private static void testCharAtValidIndex() {
        byte[] value = {0, 1, 2, 3, 4, 5};
        for (int i = 0; i < value.length; i++) {
            char c = java.lang.StringLatin1.charAt(value, i);
            if (c != (char)(value[i] & 0xff)) {
                throw new AssertionError("charAt returned wrong char at index " + i);
            }
        }
    }

    private static void testCharAtInvalidIndexNegative() {
        byte[] value = {10, 20, 30};
        try {
            java.lang.StringLatin1.charAt(value, -1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCharAtInvalidIndexTooLarge() {
        byte[] value = {10, 20, 30};
        try {
            java.lang.StringLatin1.charAt(value, value.length);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index >= length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
