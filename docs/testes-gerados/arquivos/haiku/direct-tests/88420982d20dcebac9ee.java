/*
 * @test
 * @summary Verify StringBuffer.insert(int, float) inserts the String form of the float at the given offset and returns this buffer
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertAtStartReturnsSameInstance();
        testInsertAtEnd();
        testInsertInMiddle();
        testInsertIntoEmptyBuffer();
        testInsertNegativeZero();
        testInsertNaN();
        testInsertNegativeInfinity();
        testChainedInserts();
    }

    static void testInsertAtStartReturnsSameInstance() {
        StringBuffer sb = new StringBuffer("abc");
        StringBuffer ret = sb.insert(0, 1.5f);
        check(ret == sb, "insert must return this buffer");
        check("1.5abc".equals(sb.toString()), "expected 1.5abc but was " + sb);
        check(sb.length() == 6, "unexpected length " + sb.length());
    }

    static void testInsertAtEnd() {
        StringBuffer sb = new StringBuffer("abc");
        sb.insert(3, 2.0f);
        check("abc2.0".equals(sb.toString()), "expected abc2.0 but was " + sb);
    }

    static void testInsertInMiddle() {
        StringBuffer sb = new StringBuffer("ac");
        sb.insert(1, -0.0f);
        check("a-0.0c".equals(sb.toString()), "expected a-0.0c but was " + sb);
    }

    static void testInsertIntoEmptyBuffer() {
        StringBuffer sb = new StringBuffer();
        sb.insert(0, 0.1f);
        check("0.1".equals(sb.toString()), "expected 0.1 but was " + sb);
    }

    static void testInsertNegativeZero() {
        StringBuffer sb = new StringBuffer();
        sb.insert(0, -0.0f);
        check("-0.0".equals(sb.toString()), "expected -0.0 but was " + sb);
    }

    static void testInsertNaN() {
        StringBuffer sb = new StringBuffer();
        sb.insert(0, Float.NaN);
        check("NaN".equals(sb.toString()), "expected NaN but was " + sb);
    }

    static void testInsertNegativeInfinity() {
        StringBuffer sb = new StringBuffer();
        sb.insert(0, Float.NEGATIVE_INFINITY);
        check("-Infinity".equals(sb.toString()), "expected -Infinity but was " + sb);
    }

    static void testChainedInserts() {
        StringBuffer sb = new StringBuffer("x");
        sb.insert(0, 1f).insert(0, 2f);
        check("2.01.0x".equals(sb.toString()), "expected 2.01.0x but was " + sb);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
