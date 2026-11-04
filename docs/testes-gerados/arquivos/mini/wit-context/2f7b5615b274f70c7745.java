/*
 * @test
 * @summary Test StringBuilder.insert(int, float) for valid and invalid offsets
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testInsertValidOffset();
        testInsertInvalidNegativeOffset();
        testInsertInvalidOffsetGreaterThanLength();
    }

    private static void testInsertValidOffset() {
        StringBuilder sb = new StringBuilder("hello");
        sb.insert(0, 1.5f);
        if (!sb.toString().startsWith("1.5")) {
            throw new AssertionError("Insert at offset 0 failed: " + sb);
        }
        sb = new StringBuilder("hello");
        sb.insert(5, 2.5f);
        if (!sb.toString().endsWith("2.5")) {
            throw new AssertionError("Insert at offset length failed: " + sb);
        }
        sb = new StringBuilder("hello");
        sb.insert(3, 3.5f);
        if (!sb.toString().contains("3.5")) {
            throw new AssertionError("Insert at middle offset failed: " + sb);
        }
    }

    private static void testInsertInvalidNegativeOffset() {
        StringBuilder sb = new StringBuilder("test");
        try {
            sb.insert(-1, 1.0f);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertInvalidOffsetGreaterThanLength() {
        StringBuilder sb = new StringBuilder("test");
        try {
            sb.insert(sb.length() + 1, 1.0f);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
