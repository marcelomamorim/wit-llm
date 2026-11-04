/*
 * @test
 * @summary Test java.lang.StringLatin1.charAt(byte[], int) method
 * @run main java.lang.StringLatin1WitupTest
 */

public class StringLatin1WitupTest {
    public static void main(String[] args) {
        testCharAtValidIndex();
        testCharAtIndexZero();
        testCharAtIndexLast();
        testCharAtIndexNegative();
        testCharAtIndexEqualLength();
    }

    private static void testCharAtValidIndex() {
        byte[] value = {0, 1, 2, 3, 4, 5};
        char c = java.lang.StringLatin1.charAt(value, 3);
        if (c != 3) {
            throw new AssertionError("Expected char 3 but got " + (int)c);
        }
    }

    private static void testCharAtIndexZero() {
        byte[] value = {10, 20, 30};
        char c = java.lang.StringLatin1.charAt(value, 0);
        if (c != 10) {
            throw new AssertionError("Expected char 10 but got " + (int)c);
        }
    }

    private static void testCharAtIndexLast() {
        byte[] value = {100, 101, 102};
        char c = java.lang.StringLatin1.charAt(value, 2);
        if (c != 102) {
            throw new AssertionError("Expected char 102 but got " + (int)c);
        }
    }

    private static void testCharAtIndexNegative() {
        byte[] value = {1, 2, 3};
        try {
            java.lang.StringLatin1.charAt(value, -1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index -1");
        } catch (StringIndexOutOfBoundsException e) {
            if (e.getMessage() == null || !e.getMessage().contains("-1")) {
                throw new AssertionError("Exception message does not contain index -1");
            }
        }
    }

    private static void testCharAtIndexEqualLength() {
        byte[] value = {1, 2, 3};
        try {
            java.lang.StringLatin1.charAt(value, 3);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index 3");
        } catch (StringIndexOutOfBoundsException e) {
            if (e.getMessage() == null || !e.getMessage().contains("3")) {
                throw new AssertionError("Exception message does not contain index 3");
            }
        }
    }
}
