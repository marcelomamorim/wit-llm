/*
 * @test
 * @summary Test StringBuffer.insert(int, boolean) for valid and invalid offsets
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testInsertBooleanValid();
        testInsertBooleanInvalidNegativeOffset();
        testInsertBooleanInvalidOffsetGreaterThanLength();
    }

    private static void testInsertBooleanValid() {
        StringBuffer sb = new StringBuffer("abc");
        sb.insert(0, true);
        if (!sb.toString().equals("trueabc")) {
            throw new Error("Failed insert boolean at offset 0");
        }
        sb.insert(4, false);
        if (!sb.toString().equals("truefalseabc")) {
            throw new Error("Failed insert boolean at offset 4");
        }
        sb.insert(sb.length(), true);
        if (!sb.toString().equals("truefalseabctrue")) {
            throw new Error("Failed insert boolean at end");
        }
    }

    private static void testInsertBooleanInvalidNegativeOffset() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.insert(-1, true);
            throw new Error("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertBooleanInvalidOffsetGreaterThanLength() {
        StringBuffer sb = new StringBuffer("abc");
        int invalidOffset = sb.length() + 1;
        try {
            sb.insert(invalidOffset, false);
            throw new Error("Expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
