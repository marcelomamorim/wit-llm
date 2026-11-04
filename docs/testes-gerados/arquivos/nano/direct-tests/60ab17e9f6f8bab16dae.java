/* @test    @summary Test StringBuffer.insert(int, int) method for correctness and exception handling    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertWithValidOffset();
        testInsertWithNegativeOffset();
        testInsertWithOffsetGreaterThanLength();
        testInsertWithZeroOffset();
        testInsertWithLargeInteger();
    }

    static void testInsertWithValidOffset() {
        StringBuffer sb = new StringBuffer("abc");
        sb.insert(1, 123);
        assert "a123bc".equals(sb.toString()) : "Failed: insert at valid offset";
    }

    static void testInsertWithNegativeOffset() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.insert(-1, 456);
            assert false : "Expected StringIndexOutOfBoundsException for negative offset";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testInsertWithOffsetGreaterThanLength() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.insert(10, 789);
            assert false : "Expected StringIndexOutOfBoundsException for offset greater than length";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testInsertWithZeroOffset() {
        StringBuffer sb = new StringBuffer("abc");
        sb.insert(0, 999);
        assert "999abc".equals(sb.toString()) : "Failed: insert at zero offset";
    }

    static void testInsertWithLargeInteger() {
        StringBuffer sb = new StringBuffer("test");
        sb.insert(2, Integer.MAX_VALUE);
        assert "te2147483647st".equals(sb.toString()) : "Failed: insert with large integer";
    }
}
