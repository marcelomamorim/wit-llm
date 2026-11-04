/*
 * @test
 * @summary Verifies StringBuffer.insert(int, double) inserts the Double.toString form at valid offsets, returns this, and rejects invalid offsets
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertAtStart();
        testInsertInMiddle();
        testInsertAtEnd();
        testReturnsSameInstance();
        testSpecialValues();
        testNegativeOffsetRejected();
        testOffsetBeyondLengthRejected();
    }

    static void testInsertAtStart() {
        StringBuffer sb = new StringBuffer("ab");
        sb.insert(0, 2.0);
        check("2.0ab".equals(sb.toString()), "insert at 0: " + sb);
    }

    static void testInsertInMiddle() {
        StringBuffer sb = new StringBuffer("ab");
        sb.insert(1, 1.5);
        check("a1.5b".equals(sb.toString()), "insert at 1: " + sb);
    }

    static void testInsertAtEnd() {
        StringBuffer sb = new StringBuffer("ab");
        sb.insert(2, 100.0);
        check("ab100.0".equals(sb.toString()), "insert at end: " + sb);
    }

    static void testReturnsSameInstance() {
        StringBuffer sb = new StringBuffer();
        StringBuffer r = sb.insert(0, 3.25);
        check(r == sb, "insert must return this");
        check("3.25".equals(sb.toString()), "content after insert on empty buffer: " + sb);
    }

    static void testSpecialValues() {
        StringBuffer sb = new StringBuffer("x");
        sb.insert(1, Double.NaN);
        check("xNaN".equals(sb.toString()), "NaN insert: " + sb);

        sb = new StringBuffer("x");
        sb.insert(0, Double.POSITIVE_INFINITY);
        check("Infinityx".equals(sb.toString()), "+Infinity insert: " + sb);

        sb = new StringBuffer("x");
        sb.insert(1, -0.0);
        check("x-0.0".equals(sb.toString()), "-0.0 insert: " + sb);
    }

    static void testNegativeOffsetRejected() {
        StringBuffer sb = new StringBuffer("ab");
        try {
            sb.insert(-1, 1.0);
            throw new RuntimeException("expected StringIndexOutOfBoundsException for offset -1");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
        check("ab".equals(sb.toString()), "buffer changed after rejected insert: " + sb);
    }

    static void testOffsetBeyondLengthRejected() {
        StringBuffer sb = new StringBuffer("ab");
        try {
            sb.insert(3, 1.0);
            throw new RuntimeException("expected StringIndexOutOfBoundsException for offset 3");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
        check("ab".equals(sb.toString()), "buffer changed after rejected insert: " + sb);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
