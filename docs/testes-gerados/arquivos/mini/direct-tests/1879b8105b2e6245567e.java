/*
 * @test
 * @summary Test java.lang.StringBuffer.setCharAt(int,char) method
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testSetCharAtValidIndex();
        testSetCharAtFirstAndLastIndex();
        testSetCharAtIndexOutOfBounds();
    }

    private static void testSetCharAtValidIndex() {
        StringBuffer sb = new StringBuffer("hello");
        sb.setCharAt(1, 'a');
        String result = sb.toString();
        if (!"hallo".equals(result)) {
            throw new RuntimeException("testSetCharAtValidIndex failed: expected 'hallo' but got '" + result + "'");
        }
    }

    private static void testSetCharAtFirstAndLastIndex() {
        StringBuffer sb = new StringBuffer("abc");
        sb.setCharAt(0, 'x');
        sb.setCharAt(2, 'z');
        String result = sb.toString();
        if (!"xbz".equals(result)) {
            throw new RuntimeException("testSetCharAtFirstAndLastIndex failed: expected 'xbz' but got '" + result + "'");
        }
    }

    private static void testSetCharAtIndexOutOfBounds() {
        StringBuffer sb = new StringBuffer("abc");
        boolean caughtIndexOutOfBounds = false;
        try {
            sb.setCharAt(-1, 'x');
        } catch (IndexOutOfBoundsException e) {
            caughtIndexOutOfBounds = true;
        }
        if (!caughtIndexOutOfBounds) {
            throw new RuntimeException("testSetCharAtIndexOutOfBounds failed: expected IndexOutOfBoundsException for negative index");
        }

        caughtIndexOutOfBounds = false;
        try {
            sb.setCharAt(3, 'x'); // index == length
        } catch (IndexOutOfBoundsException e) {
            caughtIndexOutOfBounds = true;
        }
        if (!caughtIndexOutOfBounds) {
            throw new RuntimeException("testSetCharAtIndexOutOfBounds failed: expected IndexOutOfBoundsException for index == length");
        }
    }
}
