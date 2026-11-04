/*
 * @test
 * @summary Test StringBuilder.insert(int, boolean) for valid and invalid offsets
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testInsertBooleanValid();
        testInsertBooleanInvalidNegativeOffset();
        testInsertBooleanInvalidOffsetTooLarge();
    }

    private static void testInsertBooleanValid() {
        StringBuilder sb = new StringBuilder("abc");
        // Insert true at offset 0
        StringBuilder result = sb.insert(0, true);
        if (!result.toString().equals("trueabc")) {
            throw new Error("Failed insert boolean at offset 0: " + result);
        }
        // Insert false at offset 4 (after 'true')
        result = sb.insert(4, false);
        if (!result.toString().equals("truefalseabc")) {
            throw new Error("Failed insert boolean at offset 4: " + result);
        }
    }

    private static void testInsertBooleanInvalidNegativeOffset() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.insert(-1, true);
            throw new Error("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected
        }
    }

    private static void testInsertBooleanInvalidOffsetTooLarge() {
        StringBuilder sb = new StringBuilder("abc");
        int invalidOffset = sb.length() + 1; // offset > length
        try {
            sb.insert(invalidOffset, false);
            throw new Error("Expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected
        }
    }
}
