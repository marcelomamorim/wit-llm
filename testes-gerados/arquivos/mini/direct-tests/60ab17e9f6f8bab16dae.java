/*
 * @test
 * @summary Test StringBuffer.insert(int, int) method behavior
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testInsertAtZero();
        testInsertAtEnd();
        testInsertInMiddle();
        testInsertNegativeOffset();
        testInsertOffsetGreaterThanLength();
    }

    private static void testInsertAtZero() {
        StringBuffer sb = new StringBuffer("abc");
        sb.insert(0, 123);
        String result = sb.toString();
        if (!"123abc".equals(result)) {
            throw new RuntimeException("testInsertAtZero failed: expected '123abc' but got '" + result + "'");
        }
    }

    private static void testInsertAtEnd() {
        StringBuffer sb = new StringBuffer("abc");
        sb.insert(sb.length(), 456);
        String result = sb.toString();
        if (!"abc456".equals(result)) {
            throw new RuntimeException("testInsertAtEnd failed: expected 'abc456' but got '" + result + "'");
        }
    }

    private static void testInsertInMiddle() {
        StringBuffer sb = new StringBuffer("abc");
        sb.insert(1, 789);
        String result = sb.toString();
        if (!"a789bc".equals(result)) {
            throw new RuntimeException("testInsertInMiddle failed: expected 'a789bc' but got '" + result + "'");
        }
    }

    private static void testInsertNegativeOffset() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.insert(-1, 10);
            throw new RuntimeException("testInsertNegativeOffset failed: expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertOffsetGreaterThanLength() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.insert(sb.length() + 1, 10);
            throw new RuntimeException("testInsertOffsetGreaterThanLength failed: expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
