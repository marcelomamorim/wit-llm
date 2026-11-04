/*
 * @test
 * @summary Test StringBuffer.insert(int, long) method behavior
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testInsertLongAtStart();
        testInsertLongAtMiddle();
        testInsertLongAtEnd();
        testInsertLongEmptyBuffer();
        testInsertLongNegativeOffset();
        testInsertLongOffsetTooLarge();
    }

    private static void testInsertLongAtStart() {
        StringBuffer sb = new StringBuffer("abc");
        sb.insert(0, 12345L);
        String result = sb.toString();
        if (!result.equals("12345abc")) {
            throw new Error("testInsertLongAtStart failed: got '" + result + "'");
        }
    }

    private static void testInsertLongAtMiddle() {
        StringBuffer sb = new StringBuffer("abcdef");
        sb.insert(3, 6789L);
        String result = sb.toString();
        if (!result.equals("abc6789def")) {
            throw new Error("testInsertLongAtMiddle failed: got '" + result + "'");
        }
    }

    private static void testInsertLongAtEnd() {
        StringBuffer sb = new StringBuffer("xyz");
        sb.insert(sb.length(), 0L);
        String result = sb.toString();
        if (!result.equals("xyz0")) {
            throw new Error("testInsertLongAtEnd failed: got '" + result + "'");
        }
    }

    private static void testInsertLongEmptyBuffer() {
        StringBuffer sb = new StringBuffer();
        sb.insert(0, -999L);
        String result = sb.toString();
        if (!result.equals("-999")) {
            throw new Error("testInsertLongEmptyBuffer failed: got '" + result + "'");
        }
    }

    private static void testInsertLongNegativeOffset() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.insert(-1, 123L);
            throw new Error("testInsertLongNegativeOffset failed: expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertLongOffsetTooLarge() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.insert(4, 123L); // offset > length
            throw new Error("testInsertLongOffsetTooLarge failed: expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
