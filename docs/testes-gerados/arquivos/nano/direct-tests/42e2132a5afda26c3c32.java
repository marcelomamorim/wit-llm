/* @test    @summary Test StringBuffer.insert(int, char[]) method for correct insertion behavior    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertAtBeginning();
        testInsertInMiddle();
        testInsertAtEnd();
        testInsertWithEmptyCharArray();
        testInsertWithNullCharArray();
    }

    private static void testInsertAtBeginning() {
        StringBuffer sb = new StringBuffer("world");
        char[] chars = {'H', 'e', 'l', 'l', 'o'};
        sb.insert(0, chars);
        assert "Hello world".equals(sb.toString()) : "Failed insert at beginning";
    }

    private static void testInsertInMiddle() {
        StringBuffer sb = new StringBuffer("Helloworld");
        char[] chars = {' ', 'J', 'a', 'v', 'a'};
        sb.insert(5, chars);
        assert "Hello Javaworld".equals(sb.toString()) : "Failed insert in middle";
    }

    private static void testInsertAtEnd() {
        StringBuffer sb = new StringBuffer("Hello");
        char[] chars = {' ', 'W', 'o', 'r', 'l', 'd'};
        sb.insert(sb.length(), chars);
        assert "Hello World".equals(sb.toString()) : "Failed insert at end";
    }

    private static void testInsertWithEmptyCharArray() {
        StringBuffer sb = new StringBuffer("Test");
        char[] empty = {};
        sb.insert(2, empty);
        assert "TeTestst".equals(sb.toString()) : "Failed insert with empty array";
    }

    private static void testInsertWithNullCharArray() {
        StringBuffer sb = new StringBuffer("NullTest");
        try {
            sb.insert(0, (char[]) null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
