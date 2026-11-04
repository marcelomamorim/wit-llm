/*
 * @test
 * @summary Tests observable behavior of StringBuilder.insert(int offset, long l)
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertMiddle();
        testReturnsSameInstance();
        testInsertAtEnd();
        testInsertIntoEmpty();
        testInsertNegativeLong();
        testInsertMinValue();
        testInvalidOffsetNegative();
        testInvalidOffsetBeyondLength();
    }

    static void testInsertMiddle() {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(1, 123L);
        check("a123b".equals(sb.toString()), "insert middle: " + sb);
    }

    static void testReturnsSameInstance() {
        StringBuilder sb = new StringBuilder("x");
        StringBuilder r = sb.insert(0, 7L);
        check(r == sb, "insert must return this");
        check("7x".equals(sb.toString()), "content after insert: " + sb);
    }

    static void testInsertAtEnd() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(3, 42L);
        check("abc42".equals(sb.toString()), "insert at end: " + sb);
    }

    static void testInsertIntoEmpty() {
        StringBuilder sb = new StringBuilder();
        sb.insert(0, 0L);
        check("0".equals(sb.toString()), "insert into empty: " + sb);
    }

    static void testInsertNegativeLong() {
        StringBuilder sb = new StringBuilder("z");
        sb.insert(0, -15L);
        check("-15z".equals(sb.toString()), "negative long: " + sb);
    }

    static void testInsertMinValue() {
        StringBuilder sb = new StringBuilder();
        sb.insert(0, Long.MIN_VALUE);
        check("-9223372036854775808".equals(sb.toString()), "Long.MIN_VALUE: " + sb);
    }

    static void testInvalidOffsetNegative() {
        StringBuilder sb = new StringBuilder("ab");
        boolean thrown = false;
        try {
            sb.insert(-1, 1L);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "negative offset must throw IndexOutOfBoundsException");
        check("ab".equals(sb.toString()), "content must be unchanged: " + sb);
    }

    static void testInvalidOffsetBeyondLength() {
        StringBuilder sb = new StringBuilder("ab");
        boolean thrown = false;
        try {
            sb.insert(3, 1L);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "offset > length must throw IndexOutOfBoundsException");
        check("ab".equals(sb.toString()), "content must be unchanged: " + sb);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
