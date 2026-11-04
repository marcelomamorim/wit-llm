/*
 * @test
 * @summary Test StringBuilder.append(CharSequence s, int start, int end) for valid and invalid ranges
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testValidAppend();
        testInvalidAppendThrows();
        testNullAppendThrows();
    }

    private static void testValidAppend() {
        StringBuilder sb = new StringBuilder("Hello");
        CharSequence cs = "World";
        // Append substring "orl" from cs
        sb.append(cs, 1, 4);
        String result = sb.toString();
        if (!result.equals("Helloorl")) {
            throw new Error("testValidAppend failed: expected 'Helloorl' but got '" + result + "'");
        }
    }

    private static void testInvalidAppendThrows() {
        StringBuilder sb = new StringBuilder();
        CharSequence cs = "abc";
        // Test start < 0
        try {
            sb.append(cs, -1, 2);
            throw new Error("Expected IndexOutOfBoundsException for start < 0");
        } catch (IndexOutOfBoundsException expected) {
        }
        // Test end < start
        try {
            sb.append(cs, 2, 1);
            throw new Error("Expected IndexOutOfBoundsException for end < start");
        } catch (IndexOutOfBoundsException expected) {
        }
        // Test end > length
        try {
            sb.append(cs, 0, 4);
            throw new Error("Expected IndexOutOfBoundsException for end > length");
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    private static void testNullAppendThrows() {
        StringBuilder sb = new StringBuilder();
        // According to StringBuilder source, passing null as s throws NullPointerException
        try {
            sb.append(null, 0, 0);
            throw new Error("Expected NullPointerException for null CharSequence");
        } catch (NullPointerException expected) {
        }
    }
}
