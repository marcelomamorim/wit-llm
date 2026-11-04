/*
 * @test
 * @summary Test StringBuffer.subSequence(int, int) method
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testSubSequenceBasic();
        testSubSequenceFullRange();
        testSubSequenceEmpty();
        testSubSequenceInvalidRange();
    }

    private static void testSubSequenceBasic() {
        StringBuffer sb = new StringBuffer("Hello, world!");
        CharSequence cs = sb.subSequence(7, 12);
        if (!"world".contentEquals(cs)) {
            throw new RuntimeException("Expected 'world' but got '" + cs + "'");
        }
    }

    private static void testSubSequenceFullRange() {
        StringBuffer sb = new StringBuffer("Hello");
        CharSequence cs = sb.subSequence(0, sb.length());
        if (!"Hello".contentEquals(cs)) {
            throw new RuntimeException("Expected 'Hello' but got '" + cs + "'");
        }
    }

    private static void testSubSequenceEmpty() {
        StringBuffer sb = new StringBuffer("abc");
        CharSequence cs = sb.subSequence(1, 1);
        if (cs.length() != 0) {
            throw new RuntimeException("Expected empty subsequence but got length " + cs.length());
        }
    }

    private static void testSubSequenceInvalidRange() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.subSequence(2, 1);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for start > end");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.subSequence(-1, 2);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for negative start");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.subSequence(0, 5);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for end > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
