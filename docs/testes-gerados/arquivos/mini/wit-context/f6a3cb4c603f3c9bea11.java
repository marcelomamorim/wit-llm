/*
 * @test
 * @summary Test StringBuffer.append(char[], int, int) for valid and invalid inputs
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testAppendValid();
        testAppendInvalidOffset();
        testAppendInvalidLen();
        testAppendInvalidOffsetLenCombination();
    }

    private static void testAppendValid() {
        StringBuffer sb = new StringBuffer("base");
        char[] chars = {'a', 'b', 'c', 'd', 'e'};
        // Append subset "bcd"
        sb.append(chars, 1, 3);
        String result = sb.toString();
        if (!"basebcd".equals(result)) {
            throw new AssertionError("Expected 'basebcd' but got '" + result + "'");
        }
    }

    private static void testAppendInvalidOffset() {
        StringBuffer sb = new StringBuffer();
        char[] chars = {'x', 'y', 'z'};
        // offset < 0 should cause IndexOutOfBoundsException
        try {
            sb.append(chars, -1, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for offset < 0");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testAppendInvalidLen() {
        StringBuffer sb = new StringBuffer();
        char[] chars = {'x', 'y', 'z'};
        // len < 0 should cause IndexOutOfBoundsException
        try {
            sb.append(chars, 0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for len < 0");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testAppendInvalidOffsetLenCombination() {
        StringBuffer sb = new StringBuffer();
        char[] chars = {'x', 'y', 'z'};
        // len > -offset + str.length (len + offset > str.length) should cause IndexOutOfBoundsException
        try {
            sb.append(chars, 2, 2); // offset=2, len=2, chars.length=3, 2+2=4 > 3
            throw new AssertionError("Expected IndexOutOfBoundsException for len + offset > str.length");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
