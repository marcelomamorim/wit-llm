/*
 * @test
 * @summary Verifies StringBuffer.insert(int, char) inserts at valid offsets and throws StringIndexOutOfBoundsException for offsets outside [0, length], leaving the buffer unchanged on failure.
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testValidInsertions();
        testInsertAtBoundaries();
        testNegativeOffsetThrows();
        testOffsetBeyondLengthThrows();
        testBufferUnchangedAfterFailedInsert();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testValidInsertions() {
        StringBuffer sb = new StringBuffer("abc");
        StringBuffer result = sb.insert(1, 'X');
        check(result == sb, "insert must return this buffer");
        check("aXbc".equals(sb.toString()), "expected aXbc but was " + sb);
    }

    static void testInsertAtBoundaries() {
        StringBuffer sb = new StringBuffer("abc");
        sb.insert(0, 'x');
        check("xabc".equals(sb.toString()), "insert at 0 failed: " + sb);
        sb.insert(sb.length(), 'z');
        check("xabcz".equals(sb.toString()), "insert at length failed: " + sb);

        StringBuffer empty = new StringBuffer();
        empty.insert(0, 'q');
        check("q".equals(empty.toString()), "insert into empty buffer failed: " + empty);
    }

    static void testNegativeOffsetThrows() {
        StringBuffer sb = new StringBuffer("abc");
        boolean thrown = false;
        try {
            sb.insert(-1, 'x');
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected StringIndexOutOfBoundsException for offset -1");
    }

    static void testOffsetBeyondLengthThrows() {
        StringBuffer sb = new StringBuffer("abc");
        boolean thrown = false;
        try {
            sb.insert(sb.length() + 1, 'x');
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected StringIndexOutOfBoundsException for offset length+1");
    }

    static void testBufferUnchangedAfterFailedInsert() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.insert(10, 'x');
            throw new RuntimeException("expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        check("abc".equals(sb.toString()), "buffer modified after failed insert: " + sb);
    }
}
