/*
 * @test
 * @summary Test StringBuffer.insert(int, long) for valid and invalid offsets
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testInsertValidOffset();
        testInsertInvalidOffsetNegative();
        testInsertInvalidOffsetTooLarge();
    }

    private static void testInsertValidOffset() {
        StringBuffer sb = new StringBuffer("hello");
        StringBuffer result = sb.insert(3, 12345L);
        if (result != sb) {
            throw new AssertionError("insert should return this");
        }
        String expected = "hel12345lo";
        if (!sb.toString().equals(expected)) {
            throw new AssertionError("Expected '" + expected + "' but got '" + sb.toString() + "'");
        }
    }

    private static void testInsertInvalidOffsetNegative() {
        StringBuffer sb = new StringBuffer("test");
        try {
            sb.insert(-1, 10L);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertInvalidOffsetTooLarge() {
        StringBuffer sb = new StringBuffer("test");
        try {
            sb.insert(sb.length() + 1, 10L);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
