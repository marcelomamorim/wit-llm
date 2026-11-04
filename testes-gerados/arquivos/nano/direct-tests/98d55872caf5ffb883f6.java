/* @test    @summary Test StringBuffer.insert(int, String) for correctness and regressions    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertAtBeginning();
        testInsertInMiddle();
        testInsertAtEnd();
        testInsertWithEmptyString();
        testInsertWithNullString();
    }

    private static void testInsertAtBeginning() {
        StringBuffer sb = new StringBuffer("world");
        sb.insert(0, "hello ");
        assert "hello world".equals(sb.toString()) : "Failed insert at beginning";
    }

    private static void testInsertInMiddle() {
        StringBuffer sb = new StringBuffer("hello world");
        sb.insert(6, "beautiful ");
        assert "hello beautiful world".equals(sb.toString()) : "Failed insert in middle";
    }

    private static void testInsertAtEnd() {
        StringBuffer sb = new StringBuffer("hello");
        sb.insert(sb.length(), " world");
        assert "hello world".equals(sb.toString()) : "Failed insert at end";
    }

    private static void testInsertWithEmptyString() {
        StringBuffer sb = new StringBuffer("test");
        sb.insert(2, "");
        assert "test".equals(sb.toString()) : "Failed insert with empty string";
    }

    private static void testInsertWithNullString() {
        StringBuffer sb = new StringBuffer("null test");
        try {
            sb.insert(0, (String) null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
