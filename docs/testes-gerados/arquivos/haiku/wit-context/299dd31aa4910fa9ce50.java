/*
 * @test
 * @summary Checks StringBuffer.codePointCount bounds validation and code point counting
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer("abc");
        check(sb.codePointCount(0, 3) == 3, "full range should count 3");
        check(sb.codePointCount(1, 1) == 0, "empty range should count 0");
        check(sb.codePointCount(1, 2) == 1, "single char range should count 1");

        String sup = "a" + (char) 0xD83D + (char) 0xDE00 + "b";
        StringBuffer sp = new StringBuffer(sup);
        check(sp.length() == 4, "surrogate pair buffer length should be 4");
        check(sp.codePointCount(0, 4) == 3, "surrogate pair should count as one code point");
        check(sp.codePointCount(1, 3) == 1, "range covering surrogate pair should count 1");

        StringBuffer lone = new StringBuffer(String.valueOf((char) 0xD83D));
        check(lone.codePointCount(0, 1) == 1, "lone high surrogate should count 1");

        expectIndexOutOfBounds(sb, -1, 2);
        expectIndexOutOfBounds(sb, 2, 1);
        expectIndexOutOfBounds(sb, 0, 4);
        expectIndexOutOfBounds(sb, 4, 4);
    }

    static void expectIndexOutOfBounds(StringBuffer sb, int begin, int end) {
        try {
            sb.codePointCount(begin, end);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for begin=" + begin + " end=" + end);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
