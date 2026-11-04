/*
 * @test
 * @summary Test StringBuilder.append(char[], int, int) for valid and invalid ranges
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testAppendValid();
        testAppendInvalidOffset();
        testAppendInvalidLen();
        testAppendInvalidOffsetLenCombination();
    }

    private static void testAppendValid() {
        char[] chars = {'a', 'b', 'c', 'd', 'e'};
        StringBuilder sb = new StringBuilder("start");
        // Append full array
        sb.append(chars, 0, chars.length);
        if (!sb.toString().equals("startabcde")) {
            throw new Error("Failed to append full char array");
        }
        // Append partial array
        sb = new StringBuilder();
        sb.append(chars, 1, 3); // b,c,d
        if (!sb.toString().equals("bcd")) {
            throw new Error("Failed to append partial char array");
        }
    }

    private static void testAppendInvalidOffset() {
        char[] chars = {'x', 'y', 'z'};
        StringBuilder sb = new StringBuilder();
        // offset < 0 should throw IndexOutOfBoundsException
        try {
            sb.append(chars, -1, 1);
            throw new Error("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testAppendInvalidLen() {
        char[] chars = {'x', 'y', 'z'};
        StringBuilder sb = new StringBuilder();
        // len < 0 should throw IndexOutOfBoundsException
        try {
            sb.append(chars, 0, -1);
            throw new Error("Expected IndexOutOfBoundsException for negative len");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testAppendInvalidOffsetLenCombination() {
        char[] chars = {'x', 'y', 'z'};
        StringBuilder sb = new StringBuilder();
        // len > str.length - offset should throw IndexOutOfBoundsException
        try {
            sb.append(chars, 2, 2); // 2 + 2 > 3
            throw new Error("Expected IndexOutOfBoundsException for len > str.length - offset");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
