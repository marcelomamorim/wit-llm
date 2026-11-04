/*
 * @test
 * @summary Test StringBuilder.insert(int offset, int i) for valid and invalid offsets
 * @run main java.lang.StringBuilderWitupTest
 */

public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testInsertValid();
        testInsertInvalidNegativeOffset();
        testInsertInvalidOffsetGreaterThanLength();
    }

    private static void testInsertValid() {
        StringBuilder sb = new StringBuilder("hello");
        sb.insert(0, 123); // insert at start
        if (!sb.toString().startsWith("123")) {
            throw new AssertionError("Insert at start failed: " + sb);
        }
        sb = new StringBuilder("hello");
        sb.insert(5, 456); // insert at end
        if (!sb.toString().endsWith("456")) {
            throw new AssertionError("Insert at end failed: " + sb);
        }
        sb = new StringBuilder("hello");
        sb.insert(3, 789); // insert in middle
        String expected = "hel789lo";
        if (!sb.toString().equals(expected)) {
            throw new AssertionError("Insert in middle failed: expected " + expected + " but got " + sb);
        }
    }

    private static void testInsertInvalidNegativeOffset() {
        StringBuilder sb = new StringBuilder("test");
        try {
            sb.insert(-1, 10);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertInvalidOffsetGreaterThanLength() {
        StringBuilder sb = new StringBuilder("test");
        int invalidOffset = sb.length() + 1;
        try {
            sb.insert(invalidOffset, 10);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
