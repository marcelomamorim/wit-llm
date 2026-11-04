/*
 * @test
 * @summary Verify StringBuffer.codePointBefore returns the code point preceding an index
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testBasic();
        testSurrogatePair();
        testLoneSurrogates();
        testAfterAppend();
        testOutOfRange();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void expectIndexOutOfBounds(StringBuffer sb, int index) {
        try {
            sb.codePointBefore(index);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected IndexOutOfBoundsException for index " + index);
    }

    static void testBasic() {
        StringBuffer sb = new StringBuffer("abc");
        check(sb.codePointBefore(1) == 'a', "codePointBefore(1) should be 'a'");
        check(sb.codePointBefore(2) == 'b', "codePointBefore(2) should be 'b'");
        check(sb.codePointBefore(3) == 'c', "codePointBefore(3) should be 'c'");
    }

    static void testSurrogatePair() {
        StringBuffer sb = new StringBuffer("a\uD83D\uDE00b");
        check(sb.length() == 4, "length should be 4");
        check(sb.codePointBefore(1) == 'a', "codePointBefore(1) should be 'a'");
        check(sb.codePointBefore(3) == 0x1F600, "codePointBefore(3) should combine surrogate pair");
        check(sb.codePointBefore(4) == 'b', "codePointBefore(4) should be 'b'");
    }

    static void testLoneSurrogates() {
        StringBuffer low = new StringBuffer("\uDE00");
        check(low.codePointBefore(1) == 0xDE00, "lone low surrogate should be returned as-is");

        StringBuffer high = new StringBuffer("\uD83Dx");
        check(high.codePointBefore(1) == 0xD83D, "lone high surrogate should be returned as-is");
        check(high.codePointBefore(2) == 'x', "codePointBefore(2) should be 'x'");
    }

    static void testAfterAppend() {
        StringBuffer sb = new StringBuffer();
        sb.append("xy");
        check(sb.codePointBefore(2) == 'y', "codePointBefore(2) after append should be 'y'");
        sb.append('z');
        check(sb.codePointBefore(3) == 'z', "codePointBefore(3) after append should be 'z'");
    }

    static void testOutOfRange() {
        expectIndexOutOfBounds(new StringBuffer(), 0);
        expectIndexOutOfBounds(new StringBuffer("ab"), 0);
        expectIndexOutOfBounds(new StringBuffer("ab"), 3);
    }
}
