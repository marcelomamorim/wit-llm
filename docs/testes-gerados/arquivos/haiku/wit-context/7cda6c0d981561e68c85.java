/*
 * @test
 * @summary Witup-generated checks for StringBuffer.offsetByCodePoints index and offset handling
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        checkValue(new StringBuffer("abc"), 0, 2, 2);
        checkValue(new StringBuffer("abc"), 0, 3, 3);
        checkValue(new StringBuffer("abc"), 3, 0, 3);
        checkValue(new StringBuffer("abc"), 1, -1, 0);
        checkValue(new StringBuffer("a\uD83D\uDE00b"), 0, 1, 1);
        checkValue(new StringBuffer("a\uD83D\uDE00b"), 0, 2, 3);
        checkValue(new StringBuffer("a\uD83D\uDE00b"), 1, 1, 3);
        checkValue(new StringBuffer("a\uD83D\uDE00b"), 0, 3, 4);
        checkValue(new StringBuffer(), 0, 0, 0);

        checkThrows(new StringBuffer("abc"), -1, 0);
        checkThrows(new StringBuffer("abc"), 4, 0);
        checkThrows(new StringBuffer("abc"), 0, 4);
        checkThrows(new StringBuffer("abc"), 3, 1);
        checkThrows(new StringBuffer("abc"), 0, -1);
        checkThrows(new StringBuffer(), 0, 1);
    }

    static void checkValue(StringBuffer sb, int index, int codePointOffset, int expected) {
        int actual = sb.offsetByCodePoints(index, codePointOffset);
        if (actual != expected) {
            throw new RuntimeException("offsetByCodePoints(" + index + ", " + codePointOffset
                    + ") returned " + actual + ", expected " + expected);
        }
    }

    static void checkThrows(StringBuffer sb, int index, int codePointOffset) {
        try {
            sb.offsetByCodePoints(index, codePointOffset);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("offsetByCodePoints(" + index + ", " + codePointOffset
                + ") did not throw IndexOutOfBoundsException");
    }
}
