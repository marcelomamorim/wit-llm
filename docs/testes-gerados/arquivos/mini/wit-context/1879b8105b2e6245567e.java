/*
 * @test
 * @summary Test java.lang.StringBuffer.setCharAt(int, char) for valid and invalid indices
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testSetCharAtValidIndex();
        testSetCharAtInvalidIndexNegative();
        testSetCharAtInvalidIndexTooLarge();
    }

    private static void testSetCharAtValidIndex() {
        StringBuffer sb = new StringBuffer("hello");
        sb.setCharAt(0, 'H');
        sb.setCharAt(4, '!');
        String result = sb.toString();
        if (!"Hello".equals(result) && !"Hell!".equals(result)) {
            throw new AssertionError("Unexpected result after setCharAt valid indices: " + result);
        }
    }

    private static void testSetCharAtInvalidIndexNegative() {
        StringBuffer sb = new StringBuffer("test");
        try {
            sb.setCharAt(-1, 'x');
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testSetCharAtInvalidIndexTooLarge() {
        StringBuffer sb = new StringBuffer("test");
        try {
            sb.setCharAt(sb.length(), 'x');
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index equal to length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
