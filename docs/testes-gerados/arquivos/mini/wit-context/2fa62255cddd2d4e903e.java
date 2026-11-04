/*
 * @test
 * @summary Test StringBuilder.insert(int, char[]) for valid and invalid offsets
 * @run main java.lang.StringBuilderWitupTest
 */

public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testInsertValid();
        testInsertInvalidNegativeOffset();
        testInsertInvalidOffsetTooLarge();
    }

    private static void testInsertValid() {
        StringBuilder sb = new StringBuilder("HelloWorld");
        char[] toInsert = {' ', 'J', 'D', 'K'};
        // Insert at offset 5 (middle)
        StringBuilder result = sb.insert(5, toInsert);
        if (!result.toString().equals("Hello JDKWorld")) {
            throw new Error("testInsertValid failed: unexpected result " + result);
        }
    }

    private static void testInsertInvalidNegativeOffset() {
        StringBuilder sb = new StringBuilder("Hello");
        char[] toInsert = {'a', 'b'};
        try {
            sb.insert(-1, toInsert);
            throw new Error("testInsertInvalidNegativeOffset failed: expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testInsertInvalidOffsetTooLarge() {
        StringBuilder sb = new StringBuilder("Hello");
        char[] toInsert = {'a', 'b'};
        try {
            sb.insert(sb.length() + 1, toInsert);
            throw new Error("testInsertInvalidOffsetTooLarge failed: expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
