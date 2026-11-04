/*
 * @test
 * @summary Verifies StringBuffer.substring(int start) returns the expected suffix and rejects out-of-range starts
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testSuffixFromStart();
        testStartZeroReturnsWholeContent();
        testStartAtLengthReturnsEmptyString();
        testEmptyBufferStartZero();
        testReflectsAppendedContent();
        testResultIsIndependentCopy();
        testNegativeStartThrows();
        testStartGreaterThanLengthThrows();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testSuffixFromStart() {
        StringBuffer sb = new StringBuffer("abcdef");
        check("cdef".equals(sb.substring(2)), "substring(2) of abcdef should be cdef");
    }

    static void testStartZeroReturnsWholeContent() {
        StringBuffer sb = new StringBuffer("hello");
        check("hello".equals(sb.substring(0)), "substring(0) should return full content");
    }

    static void testStartAtLengthReturnsEmptyString() {
        StringBuffer sb = new StringBuffer("hello");
        check("".equals(sb.substring(sb.length())), "substring(length) should return empty string");
    }

    static void testEmptyBufferStartZero() {
        StringBuffer sb = new StringBuffer();
        check("".equals(sb.substring(0)), "substring(0) on empty buffer should be empty");
    }

    static void testReflectsAppendedContent() {
        StringBuffer sb = new StringBuffer("abc");
        sb.append("de");
        check("de".equals(sb.substring(3)), "substring(3) after append should be de");
    }

    static void testResultIsIndependentCopy() {
        StringBuffer sb = new StringBuffer("abc");
        String s = sb.substring(0);
        sb.setLength(0);
        check("abc".equals(s), "returned String must not change when buffer is modified");
    }

    static void testNegativeStartThrows() {
        StringBuffer sb = new StringBuffer("abc");
        boolean thrown = false;
        try {
            sb.substring(-1);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "substring(-1) should throw StringIndexOutOfBoundsException");
    }

    static void testStartGreaterThanLengthThrows() {
        StringBuffer sb = new StringBuffer("abc");
        boolean thrown = false;
        try {
            sb.substring(sb.length() + 1);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "substring(length+1) should throw StringIndexOutOfBoundsException");
    }
}
