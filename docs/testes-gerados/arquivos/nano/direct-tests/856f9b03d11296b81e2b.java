/* @test    @summary Test StringBuffer.insert(int, char) method for correct insertion at various positions    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertAtBeginning();
        testInsertInMiddle();
        testInsertAtEnd();
        testInsertWithEmptyBuffer();
        testInsertWithInvalidOffset();
    }

    private static void testInsertAtBeginning() {
        StringBuffer sb = new StringBuffer("world");
        sb.insert(0, 'H');
        assert "Hworld".equals(sb.toString()) : "Failed insert at beginning";
    }

    private static void testInsertInMiddle() {
        StringBuffer sb = new StringBuffer("Hellow");
        sb.insert(2, 'i');
        assert "Hiellow".equals(sb.toString()) : "Failed insert in middle";
    }

    private static void testInsertAtEnd() {
        StringBuffer sb = new StringBuffer("Hello");
        sb.insert(sb.length(), '!');
        assert "Hello!".equals(sb.toString()) : "Failed insert at end";
    }

    private static void testInsertWithEmptyBuffer() {
        StringBuffer sb = new StringBuffer();
        sb.insert(0, 'A');
        assert "A".equals(sb.toString()) : "Failed insert into empty buffer";
    }

    private static void testInsertWithInvalidOffset() {
        StringBuffer sb = new StringBuffer("Test");
        try {
            sb.insert(10, 'X');
            assert false : "Expected IndexOutOfBoundsException";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
