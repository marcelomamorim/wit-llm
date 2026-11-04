/*
 * @test
 * @summary Test StringBuffer.insert(int, float) for valid and invalid offsets
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
        sb.insert(0, 1.23f);
        // The float 1.23f converted to string and inserted at offset 0
        String result = sb.toString();
        if (!result.startsWith("1.23")) {
            throw new AssertionError("Expected string to start with '1.23', got: " + result);
        }
        if (!result.endsWith("hello")) {
            throw new AssertionError("Expected string to end with 'hello', got: " + result);
        }
    }

    private static void testInsertInvalidOffsetNegative() {
        StringBuffer sb = new StringBuffer("test");
        try {
            sb.insert(-1, 3.14f);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testInsertInvalidOffsetTooLarge() {
        StringBuffer sb = new StringBuffer("test");
        try {
            sb.insert(sb.length() + 1, 2.71f);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
