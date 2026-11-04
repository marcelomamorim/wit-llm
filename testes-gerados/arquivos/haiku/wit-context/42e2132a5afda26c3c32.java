/*
 * @test
 * @summary Tests StringBuffer.insert(int, char[]) boundary offsets, exception on invalid offset, buffer state preservation and return value
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertAtStartMiddleAndEnd();
        testInsertEmptyArray();
        testInsertIntoEmptyBuffer();
        testReturnsSameInstance();
        testToStringReflectsInsertAfterCachedToString();
        testNegativeOffsetThrows();
        testOffsetBeyondLengthThrows();
        testFailedInsertDoesNotModifyBuffer();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInsertAtStartMiddleAndEnd() {
        StringBuffer sb = new StringBuffer("start");
        sb.insert(4, "le".toCharArray());
        check("starlet".equals(sb.toString()), "middle insert failed: " + sb);

        sb = new StringBuffer("abc");
        sb.insert(0, new char[] {'x'});
        check("xabc".equals(sb.toString()), "start insert failed: " + sb);

        sb = new StringBuffer("abc");
        sb.insert(3, new char[] {'x', 'y'});
        check("abcxy".equals(sb.toString()), "end insert failed: " + sb);
    }

    static void testInsertEmptyArray() {
        StringBuffer sb = new StringBuffer("ab");
        sb.insert(1, new char[0]);
        check("ab".equals(sb.toString()), "empty array insert changed buffer: " + sb);
    }

    static void testInsertIntoEmptyBuffer() {
        StringBuffer sb = new StringBuffer();
        sb.insert(0, new char[] {'z'});
        check("z".equals(sb.toString()), "insert into empty buffer failed: " + sb);
    }

    static void testReturnsSameInstance() {
        StringBuffer sb = new StringBuffer();
        StringBuffer result = sb.insert(0, "hi".toCharArray());
        check(result == sb, "insert did not return this");
        check("hi".equals(sb.toString()), "content mismatch after insert: " + sb);
    }

    static void testToStringReflectsInsertAfterCachedToString() {
        StringBuffer sb = new StringBuffer("abc");
        check("abc".equals(sb.toString()), "initial toString mismatch");
        sb.insert(1, new char[] {'X'});
        check("aXbc".equals(sb.toString()), "stale toString after insert: " + sb);
    }

    static void testNegativeOffsetThrows() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.insert(-1, new char[] {'x'});
        } catch (StringIndexOutOfBoundsException e) {
            check("abc".equals(sb.toString()), "buffer modified after failed negative insert");
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for offset -1");
    }

    static void testOffsetBeyondLengthThrows() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.insert(4, new char[] {'x'});
        } catch (StringIndexOutOfBoundsException e) {
            check("abc".equals(sb.toString()), "buffer modified after failed insert beyond length");
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for offset 4 on length 3");
    }

    static void testFailedInsertDoesNotModifyBuffer() {
        StringBuffer sb = new StringBuffer();
        try {
            sb.insert(1, new char[] {'a'});
        } catch (StringIndexOutOfBoundsException e) {
            check(sb.length() == 0, "length changed after failed insert on empty buffer");
            check("".equals(sb.toString()), "content changed after failed insert on empty buffer");
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for offset 1 on empty buffer");
    }
}
