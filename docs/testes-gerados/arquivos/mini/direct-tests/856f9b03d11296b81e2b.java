/*
 * @test
 * @summary Test StringBuffer.insert(int, char) method
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testInsertCharAtBeginning();
        testInsertCharAtMiddle();
        testInsertCharAtEnd();
        testInsertCharNegativeOffset();
        testInsertCharOffsetGreaterThanLength();
    }

    private static void testInsertCharAtBeginning() {
        StringBuffer sb = new StringBuffer("bcdef");
        sb.insert(0, 'a');
        String result = sb.toString();
        if (!"abcdef".equals(result)) {
            throw new Error("Failed insert at beginning: expected 'abcdef' but got '" + result + "'");
        }
    }

    private static void testInsertCharAtMiddle() {
        StringBuffer sb = new StringBuffer("abdef");
        sb.insert(2, 'c');
        String result = sb.toString();
        if (!"abcdef".equals(result)) {
            throw new Error("Failed insert at middle: expected 'abcdef' but got '" + result + "'");
        }
    }

    private static void testInsertCharAtEnd() {
        StringBuffer sb = new StringBuffer("abcde");
        sb.insert(sb.length(), 'f');
        String result = sb.toString();
        if (!"abcdef".equals(result)) {
            throw new Error("Failed insert at end: expected 'abcdef' but got '" + result + "'");
        }
    }

    private static void testInsertCharNegativeOffset() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.insert(-1, 'x');
            throw new Error("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertCharOffsetGreaterThanLength() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.insert(sb.length() + 1, 'x');
            throw new Error("Expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
