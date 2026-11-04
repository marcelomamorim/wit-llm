/*
 * @test
 * @summary Test StringBuffer.insert(int offset, int i) for normal and exception cases
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testInsertValid();
        testInsertInvalidNegativeOffset();
        testInsertInvalidOffsetBeyondLength();
    }

    private static void testInsertValid() {
        StringBuffer sb = new StringBuffer("hello");
        sb.insert(0, 123);
        if (!sb.toString().equals("123hello")) {
            throw new Error("Failed insert at offset 0");
        }
        sb.insert(3, 456);
        if (!sb.toString().equals("123456hello")) {
            throw new Error("Failed insert at offset 3");
        }
        sb.insert(sb.length(), 789);
        if (!sb.toString().equals("123456hello789")) {
            throw new Error("Failed insert at end offset");
        }
    }

    private static void testInsertInvalidNegativeOffset() {
        StringBuffer sb = new StringBuffer("test");
        try {
            sb.insert(-1, 10);
            throw new Error("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertInvalidOffsetBeyondLength() {
        StringBuffer sb = new StringBuffer("test");
        int invalidOffset = sb.length() + 1;
        try {
            sb.insert(invalidOffset, 10);
            throw new Error("Expected StringIndexOutOfBoundsException for offset beyond length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
