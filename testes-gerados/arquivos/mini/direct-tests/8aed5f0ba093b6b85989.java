/*
 * @test
 * @summary Test StringBuilder.insert(int, char) method
 * @run main StringBuilderWitupTest
 */
public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testInsertCharAtStart();
        testInsertCharAtMiddle();
        testInsertCharAtEnd();
        testInsertCharNegativeOffset();
        testInsertCharOffsetTooLarge();
    }

    private static void testInsertCharAtStart() {
        StringBuilder sb = new StringBuilder("bc");
        sb.insert(0, 'a');
        if (!sb.toString().equals("abc")) {
            throw new Error("Failed insert at start: " + sb);
        }
    }

    private static void testInsertCharAtMiddle() {
        StringBuilder sb = new StringBuilder("ac");
        sb.insert(1, 'b');
        if (!sb.toString().equals("abc")) {
            throw new Error("Failed insert at middle: " + sb);
        }
    }

    private static void testInsertCharAtEnd() {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(sb.length(), 'c');
        if (!sb.toString().equals("abc")) {
            throw new Error("Failed insert at end: " + sb);
        }
    }

    private static void testInsertCharNegativeOffset() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.insert(-1, 'x');
            throw new Error("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertCharOffsetTooLarge() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.insert(sb.length() + 1, 'x');
            throw new Error("Expected StringIndexOutOfBoundsException for offset too large");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
