/*
 * @test
 * @summary Test java.lang.StringBuffer.delete(int, int) method
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testDeleteNormal();
        testDeleteEmptyRange();
        testDeleteFullRange();
        testDeleteInvalidRange();
    }

    private static void testDeleteNormal() {
        StringBuffer sb = new StringBuffer("Hello World");
        StringBuffer result = sb.delete(5, 6); // remove space
        if (result != sb) throw new RuntimeException("delete should return this");
        if (!sb.toString().equals("HelloWorld")) {
            throw new RuntimeException("Expected 'HelloWorld' but got '" + sb.toString() + "'");
        }
    }

    private static void testDeleteEmptyRange() {
        StringBuffer sb = new StringBuffer("abc");
        StringBuffer result = sb.delete(1, 1); // empty range, no change
        if (result != sb) throw new RuntimeException("delete should return this");
        if (!sb.toString().equals("abc")) {
            throw new RuntimeException("Expected 'abc' but got '" + sb.toString() + "'");
        }
    }

    private static void testDeleteFullRange() {
        StringBuffer sb = new StringBuffer("test");
        StringBuffer result = sb.delete(0, sb.length()); // delete all
        if (result != sb) throw new RuntimeException("delete should return this");
        if (!sb.toString().equals("")) {
            throw new RuntimeException("Expected empty string but got '" + sb.toString() + "'");
        }
    }

    private static void testDeleteInvalidRange() {
        StringBuffer sb = new StringBuffer("abc");
        boolean threw = false;
        try {
            sb.delete(2, 1); // start > end, should throw StringIndexOutOfBoundsException
        } catch (StringIndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for start > end");
        }

        threw = false;
        try {
            sb.delete(-1, 1); // start < 0, should throw StringIndexOutOfBoundsException
        } catch (StringIndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for start < 0");
        }

        threw = false;
        try {
            sb.delete(0, 10); // end > length, should throw StringIndexOutOfBoundsException
        } catch (StringIndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for end > length");
        }
    }
}
