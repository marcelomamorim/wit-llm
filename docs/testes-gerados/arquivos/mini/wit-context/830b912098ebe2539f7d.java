/*
 * @test
 * @summary Test StringBuilder.insert(int, long) for valid and invalid offsets
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testInsertValid();
        testInsertInvalidNegativeOffset();
        testInsertInvalidOffsetGreaterThanLength();
        System.out.println("All tests passed.");
    }

    private static void testInsertValid() {
        StringBuilder sb = new StringBuilder("hello");
        StringBuilder result = sb.insert(3, 12345L);
        if (result != sb) {
            throw new AssertionError("insert should return this instance");
        }
        String expected = "hel12345lo";
        if (!sb.toString().equals(expected)) {
            throw new AssertionError("Expected '" + expected + "' but got '" + sb.toString() + "'");
        }
    }

    private static void testInsertInvalidNegativeOffset() {
        StringBuilder sb = new StringBuilder("test");
        try {
            sb.insert(-1, 10L);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertInvalidOffsetGreaterThanLength() {
        StringBuilder sb = new StringBuilder("test");
        int invalidOffset = sb.length() + 1;
        try {
            sb.insert(invalidOffset, 10L);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
