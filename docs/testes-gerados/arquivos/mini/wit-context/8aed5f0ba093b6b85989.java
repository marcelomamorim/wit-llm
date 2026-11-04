/*
 * @test
 * @summary Test StringBuilder.insert(int, char) for valid and invalid offsets
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
        sb.insert(0, 'X');
        if (!sb.toString().equals("Xhello")) {
            throw new Error("Failed insert at offset 0");
        }
        sb.insert(3, 'Y');
        if (!sb.toString().equals("XheYllo")) {
            throw new Error("Failed insert at offset 3");
        }
        sb.insert(sb.length(), 'Z');
        if (!sb.toString().equals("XheYlloZ")) {
            throw new Error("Failed insert at offset length");
        }
    }

    private static void testInsertInvalidNegativeOffset() {
        StringBuilder sb = new StringBuilder("test");
        try {
            sb.insert(-1, 'a');
            throw new Error("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertInvalidOffsetGreaterThanLength() {
        StringBuilder sb = new StringBuilder("test");
        try {
            sb.insert(sb.length() + 1, 'a');
            throw new Error("Expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
