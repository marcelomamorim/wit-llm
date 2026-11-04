/*
 * @test
 * @summary Tests observable behavior of StringBuffer.insert(int offset, char c)
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertAtStart();
        testInsertInMiddle();
        testInsertAtEndMatchesAppend();
        testInsertIntoEmptyBuffer();
        testReturnsSameInstance();
        testToStringCacheInvalidated();
        testInvalidOffsetThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInsertAtStart() {
        StringBuffer sb = new StringBuffer("bc");
        sb.insert(0, 'a');
        check("abc".equals(sb.toString()), "insert at 0 failed: " + sb);
    }

    static void testInsertInMiddle() {
        StringBuffer sb = new StringBuffer("ac");
        sb.insert(1, 'b');
        check("abc".equals(sb.toString()), "insert in middle failed: " + sb);
    }

    static void testInsertAtEndMatchesAppend() {
        StringBuffer sb = new StringBuffer("ab");
        sb.insert(sb.length(), 'c');
        check("abc".equals(sb.toString()), "insert at length failed: " + sb);
        check(sb.length() == 3, "length after insert at end wrong");
    }

    static void testInsertIntoEmptyBuffer() {
        StringBuffer sb = new StringBuffer();
        sb.insert(0, 'x');
        check("x".equals(sb.toString()), "insert into empty buffer failed: " + sb);
        check(sb.length() == 1, "length after insert into empty wrong");
    }

    static void testReturnsSameInstance() {
        StringBuffer sb = new StringBuffer("ab");
        StringBuffer result = sb.insert(1, 'z');
        check(result == sb, "insert must return the same StringBuffer instance");
        check("azb".equals(result.toString()), "chained result content wrong");
    }

    static void testToStringCacheInvalidated() {
        StringBuffer sb = new StringBuffer("ab");
        String before = sb.toString();
        check("ab".equals(before), "initial toString wrong");
        sb.insert(0, 'z');
        check("zab".equals(sb.toString()), "toString stale after insert");
        check("ab".equals(before), "previously returned String must not change");
    }

    static void testInvalidOffsetThrows() {
        StringBuffer sb = new StringBuffer("ab");
        boolean thrownNegative = false;
        try {
            sb.insert(-1, 'x');
        } catch (StringIndexOutOfBoundsException e) {
            thrownNegative = true;
        }
        check(thrownNegative, "negative offset must throw StringIndexOutOfBoundsException");

        boolean thrownTooLarge = false;
        try {
            sb.insert(sb.length() + 1, 'x');
        } catch (StringIndexOutOfBoundsException e) {
            thrownTooLarge = true;
        }
        check(thrownTooLarge, "offset > length must throw StringIndexOutOfBoundsException");
        check("ab".equals(sb.toString()), "failed insert must not modify buffer");
    }
}
