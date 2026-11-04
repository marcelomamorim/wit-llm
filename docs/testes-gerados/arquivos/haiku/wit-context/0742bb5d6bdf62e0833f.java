/*
 * @test
 * @summary Witup-generated checks for StringBuffer.subSequence range handling
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer("hello");

        CharSequence middle = sb.subSequence(1, 3);
        check("el".equals(middle.toString()), "subSequence(1,3) should return \"el\"");

        check(sb.subSequence(0, 0).length() == 0, "empty range at start should be empty");
        check(sb.subSequence(5, 5).length() == 0, "empty range at end should be empty");
        check("hello".equals(sb.subSequence(0, 5).toString()), "full range should return whole content");

        expectStringIndexOutOfBounds(sb, -1, 2);
        expectStringIndexOutOfBounds(sb, 0, 6);
        expectStringIndexOutOfBounds(sb, 3, 2);
        expectStringIndexOutOfBounds(sb, 6, 6);
    }

    private static void expectStringIndexOutOfBounds(StringBuffer sb, int start, int end) {
        try {
            sb.subSequence(start, end);
        } catch (StringIndexOutOfBoundsException expected) {
            return;
        }
        throw new RuntimeException("Expected StringIndexOutOfBoundsException for start=" + start + ", end=" + end);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
