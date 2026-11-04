/*
 * @test
 * @summary Test StringBuilder.insert(int, double) for valid and invalid offsets
 * @run main java.lang.StringBuilderWitupTest
 */

public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testInsertValidOffset();
        testInsertInvalidOffsetNegative();
        testInsertInvalidOffsetTooLarge();
    }

    private static void testInsertValidOffset() {
        StringBuilder sb = new StringBuilder("hello");
        StringBuilder result = sb.insert(3, 3.14d);
        if (result != sb) {
            throw new AssertionError("insert should return this");
        }
        String expected = "hel3.14lo";
        if (!sb.toString().equals(expected)) {
            throw new AssertionError("Expected '" + expected + "' but got '" + sb.toString() + "'");
        }
    }

    private static void testInsertInvalidOffsetNegative() {
        StringBuilder sb = new StringBuilder("test");
        try {
            sb.insert(-1, 1.0d);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertInvalidOffsetTooLarge() {
        StringBuilder sb = new StringBuilder("test");
        try {
            sb.insert(sb.length() + 1, 1.0d);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
