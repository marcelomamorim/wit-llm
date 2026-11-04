/*
 * @test
 * @summary Witup-generated checks for StringBuffer.insert(int, long)
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertAtStart();
        testInsertAtEndMatchesAppend();
        testInsertMinLongOnEmptyBuffer();
        testInsertReturnsSameInstance();
        testOffsetBeyondLengthThrows();
        testNegativeOffsetThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInsertAtStart() {
        StringBuffer sb = new StringBuffer("ab");
        sb.insert(0, 42L);
        check("42ab".equals(sb.toString()),
              "insert(0, 42L) on \"ab\" expected \"42ab\" but was \"" + sb + "\"");
    }

    static void testInsertAtEndMatchesAppend() {
        StringBuffer viaInsert = new StringBuffer("x");
        viaInsert.insert(viaInsert.length(), 7L);
        StringBuffer viaAppend = new StringBuffer("x");
        viaAppend.append(7L);
        check(viaInsert.toString().equals(viaAppend.toString()),
              "insert at length should match append, got \"" + viaInsert + "\"");
        check("x7".equals(viaInsert.toString()),
              "expected \"x7\" but was \"" + viaInsert + "\"");
    }

    static void testInsertMinLongOnEmptyBuffer() {
        StringBuffer sb = new StringBuffer();
        sb.insert(0, Long.MIN_VALUE);
        check("-9223372036854775808".equals(sb.toString()),
              "unexpected Long.MIN_VALUE rendering: \"" + sb + "\"");
    }

    static void testInsertReturnsSameInstance() {
        StringBuffer sb = new StringBuffer("q");
        StringBuffer result = sb.insert(0, 1L);
        check(result == sb, "insert(int, long) should return this");
        check("1q".equals(sb.toString()),
              "expected \"1q\" but was \"" + sb + "\"");
    }

    static void testOffsetBeyondLengthThrows() {
        StringBuffer sb = new StringBuffer("abc");
        boolean thrown = false;
        try {
            sb.insert(sb.length() + 1, 5L);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "offset > length should throw StringIndexOutOfBoundsException");
        check("abc".equals(sb.toString()), "buffer must be unchanged after failed insert");
    }

    static void testNegativeOffsetThrows() {
        StringBuffer sb = new StringBuffer("abc");
        boolean thrown = false;
        try {
            sb.insert(-1, 5L);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "negative offset should throw StringIndexOutOfBoundsException");
        check("abc".equals(sb.toString()), "buffer must be unchanged after failed insert");
    }
}
