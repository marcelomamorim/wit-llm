/*
 * @test
 * @summary Test StringBuilder.insert(int, String) for valid and invalid offsets
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testInsertValid();
        testInsertInvalidNegativeOffset();
        testInsertInvalidOffsetTooLarge();
    }

    private static void testInsertValid() {
        StringBuilder sb = new StringBuilder("hello");
        StringBuilder result = sb.insert(3, "X");
        if (result != sb) {
            throw new AssertionError("insert should return this");
        }
        String expected = "helXlo";
        if (!sb.toString().equals(expected)) {
            throw new AssertionError("Expected '" + expected + "' but got '" + sb.toString() + "'");
        }
    }

    private static void testInsertInvalidNegativeOffset() {
        StringBuilder sb = new StringBuilder("hello");
        try {
            sb.insert(-1, "X");
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertInvalidOffsetTooLarge() {
        StringBuilder sb = new StringBuilder("hello");
        int invalidOffset = sb.length() + 1; // offset > length
        try {
            sb.insert(invalidOffset, "X");
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
