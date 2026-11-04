/*
 * @test
 * @summary Witup-style tests for StringBuffer.insert(int, char[])
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertAtStart();
        testInsertInMiddle();
        testInsertAtEnd();
        testInsertEmptyArray();
        testReturnsSameInstance();
        testInvalidNegativeOffset();
        testInvalidOffsetBeyondLength();
        testToStringCacheInvalidatedAfterInsert();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testInsertAtStart() {
        StringBuffer sb = new StringBuffer("start");
        sb.insert(0, "le".toCharArray());
        check("lestart".equals(sb.toString()),
              "insert at 0 expected 'lestart' but was '" + sb + "'");
    }

    static void testInsertInMiddle() {
        StringBuffer sb = new StringBuffer("start");
        sb.insert(4, "le".toCharArray());
        check("starlet".equals(sb.toString()),
              "insert at 4 expected 'starlet' but was '" + sb + "'");
    }

    static void testInsertAtEnd() {
        StringBuffer sb = new StringBuffer("start");
        sb.insert(sb.length(), "le".toCharArray());
        check("startle".equals(sb.toString()),
              "insert at length expected 'startle' but was '" + sb + "'");
    }

    static void testInsertEmptyArray() {
        StringBuffer sb = new StringBuffer("abc");
        sb.insert(1, new char[0]);
        check("abc".equals(sb.toString()),
              "inserting empty array should not change content, was '" + sb + "'");
    }

    static void testReturnsSameInstance() {
        StringBuffer sb = new StringBuffer("abc");
        StringBuffer result = sb.insert(0, new char[]{'x'});
        check(result == sb, "insert(int, char[]) should return this");
    }

    static void testInvalidNegativeOffset() {
        StringBuffer sb = new StringBuffer("abc");
        boolean thrown = false;
        try {
            sb.insert(-1, new char[]{'x'});
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "negative offset should throw StringIndexOutOfBoundsException");
        check("abc".equals(sb.toString()), "failed insert should not modify content");
    }

    static void testInvalidOffsetBeyondLength() {
        StringBuffer sb = new StringBuffer("abc");
        boolean thrown = false;
        try {
            sb.insert(sb.length() + 1, new char[]{'x'});
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "offset > length should throw StringIndexOutOfBoundsException");
        check("abc".equals(sb.toString()), "failed insert should not modify content");
    }

    static void testToStringCacheInvalidatedAfterInsert() {
        StringBuffer sb = new StringBuffer("ab");
        String before = sb.toString();
        check("ab".equals(before), "initial toString mismatch");
        sb.insert(1, new char[]{'X'});
        check("aXb".equals(sb.toString()),
              "toString after insert should reflect change, was '" + sb + "'");
    }
}
