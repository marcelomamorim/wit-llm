/* @test    @summary Test StringBuffer.insert(int, long) method for correct behavior and edge cases    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertLongAtBeginning();
        testInsertLongAtEnd();
        testInsertLongAtMiddle();
        testInsertLongWithNegativeOffset();
        testInsertLongWithOffsetBeyondLength();
    }

    static void testInsertLongAtBeginning() {
        StringBuffer sb = new StringBuffer("world");
        long value = 123456789L;
        sb.insert(0, value);
        assert sb.toString().equals("123456789world") : "Failed at insertLong at beginning";
    }

    static void testInsertLongAtEnd() {
        StringBuffer sb = new StringBuffer("hello");
        long value = 987654321L;
        sb.insert(sb.length(), value);
        assert sb.toString().equals("hello987654321") : "Failed at insertLong at end";
    }

    static void testInsertLongAtMiddle() {
        StringBuffer sb = new StringBuffer("foo bar");
        long value = 55555L;
        sb.insert(4, value);
        assert sb.toString().equals("foo 55555bar") : "Failed at insertLong at middle";
    }

    static void testInsertLongWithNegativeOffset() {
        StringBuffer sb = new StringBuffer("test");
        long value = 42L;
        try {
            sb.insert(-1, value);
            assert false : "Expected StringIndexOutOfBoundsException for negative offset";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testInsertLongWithOffsetBeyondLength() {
        StringBuffer sb = new StringBuffer("abc");
        long value = 999L;
        try {
            sb.insert(10, value);
            assert false : "Expected StringIndexOutOfBoundsException for offset beyond length";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
