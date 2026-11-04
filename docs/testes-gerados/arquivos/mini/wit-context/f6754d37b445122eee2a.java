/*
 * @test
 * @summary Test StringBuffer.insert(int, double) for valid and invalid offsets
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testInsertValid();
        testInsertInvalidNegativeOffset();
        testInsertInvalidOffsetGreaterThanLength();
    }

    private static void testInsertValid() {
        StringBuffer sb = new StringBuffer("hello");
        StringBuffer result = sb.insert(0, 3.14);
        if (result != sb) {
            throw new AssertionError("insert should return this");
        }
        String expected = "3.14hello";
        if (!sb.toString().equals(expected)) {
            throw new AssertionError("Expected '" + expected + "' but got '" + sb.toString() + "'");
        }
    }

    private static void testInsertInvalidNegativeOffset() {
        StringBuffer sb = new StringBuffer("test");
        try {
            sb.insert(-1, 2.71);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertInvalidOffsetGreaterThanLength() {
        StringBuffer sb = new StringBuffer("test");
        int invalidOffset = sb.length() + 1;
        try {
            sb.insert(invalidOffset, 1.23);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
