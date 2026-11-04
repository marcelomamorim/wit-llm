/*
 * @test
 * @summary Test StringBuffer.insert(int, char[]) method behavior
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testInsertCharArrayAtStart();
        testInsertCharArrayAtMiddle();
        testInsertCharArrayAtEnd();
        testInsertCharArrayEmptyArray();
        testInsertCharArrayNull();
        testInsertCharArrayInvalidOffset();
    }

    private static void testInsertCharArrayAtStart() {
        StringBuffer sb = new StringBuffer("world");
        char[] chars = {'H', 'e', 'l', 'l', 'o', ' '};
        sb.insert(0, chars);
        String result = sb.toString();
        if (!"Hello world".equals(result)) {
            throw new Error("testInsertCharArrayAtStart failed: expected 'Hello world' but got '" + result + "'");
        }
    }

    private static void testInsertCharArrayAtMiddle() {
        StringBuffer sb = new StringBuffer("Helo");
        char[] chars = {'l'};
        sb.insert(3, chars);
        String result = sb.toString();
        if (!"Hello".equals(result)) {
            throw new Error("testInsertCharArrayAtMiddle failed: expected 'Hello' but got '" + result + "'");
        }
    }

    private static void testInsertCharArrayAtEnd() {
        StringBuffer sb = new StringBuffer("Hello");
        char[] chars = {' ', 'W', 'o', 'r', 'l', 'd'};
        sb.insert(sb.length(), chars);
        String result = sb.toString();
        if (!"Hello World".equals(result)) {
            throw new Error("testInsertCharArrayAtEnd failed: expected 'Hello World' but got '" + result + "'");
        }
    }

    private static void testInsertCharArrayEmptyArray() {
        StringBuffer sb = new StringBuffer("Hello");
        char[] chars = new char[0];
        sb.insert(2, chars);
        String result = sb.toString();
        if (!"Hello".equals(result)) {
            throw new Error("testInsertCharArrayEmptyArray failed: expected 'Hello' but got '" + result + "'");
        }
    }

    private static void testInsertCharArrayNull() {
        StringBuffer sb = new StringBuffer("Hello");
        try {
            sb.insert(1, (char[]) null);
            throw new Error("testInsertCharArrayNull failed: expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testInsertCharArrayInvalidOffset() {
        StringBuffer sb = new StringBuffer("Hello");
        char[] chars = {'X'};
        try {
            sb.insert(-1, chars);
            throw new Error("testInsertCharArrayInvalidOffset failed: expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.insert(sb.length() + 1, chars);
            throw new Error("testInsertCharArrayInvalidOffset failed: expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
