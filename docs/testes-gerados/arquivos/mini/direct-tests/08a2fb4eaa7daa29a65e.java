/*
 * @test
 * @summary Test java.lang.StringBuffer.deleteCharAt(int) method
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testDeleteCharAtValidIndex();
        testDeleteCharAtFirstChar();
        testDeleteCharAtLastChar();
        testDeleteCharAtEmptyBuffer();
        testDeleteCharAtIndexOutOfBounds();
    }

    private static void testDeleteCharAtValidIndex() {
        StringBuffer sb = new StringBuffer("hello");
        StringBuffer result = sb.deleteCharAt(1); // remove 'e'
        if (result != sb) {
            throw new RuntimeException("deleteCharAt should return this");
        }
        String expected = "hllo";
        if (!sb.toString().equals(expected)) {
            throw new RuntimeException("Expected '" + expected + "' but got '" + sb.toString() + "'");
        }
    }

    private static void testDeleteCharAtFirstChar() {
        StringBuffer sb = new StringBuffer("abc");
        sb.deleteCharAt(0); // remove 'a'
        String expected = "bc";
        if (!sb.toString().equals(expected)) {
            throw new RuntimeException("Expected '" + expected + "' but got '" + sb.toString() + "'");
        }
    }

    private static void testDeleteCharAtLastChar() {
        StringBuffer sb = new StringBuffer("abc");
        sb.deleteCharAt(sb.length() - 1); // remove 'c'
        String expected = "ab";
        if (!sb.toString().equals(expected)) {
            throw new RuntimeException("Expected '" + expected + "' but got '" + sb.toString() + "'");
        }
    }

    private static void testDeleteCharAtEmptyBuffer() {
        StringBuffer sb = new StringBuffer();
        try {
            sb.deleteCharAt(0);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for empty buffer");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testDeleteCharAtIndexOutOfBounds() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.deleteCharAt(-1);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.deleteCharAt(sb.length());
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for index == length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
