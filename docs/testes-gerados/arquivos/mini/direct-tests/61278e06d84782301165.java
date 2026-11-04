/*
 * @test
 * @summary Test StringBuilder.append(CharSequence s, int start, int end) method
 * @run main StringBuilderWitupTest
 */
public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testAppendSubSequence();
        testAppendEmptySubSequence();
        testAppendFullSequence();
        testAppendNullThrowsNPE();
        testAppendInvalidIndices();
    }

    private static void testAppendSubSequence() {
        StringBuilder sb = new StringBuilder("Hello");
        CharSequence seq = "World";
        sb.append(seq, 1, 4); // "orl"
        String result = sb.toString();
        if (!result.equals("Helloorl")) {
            throw new AssertionError("Expected 'Helloorl' but got '" + result + "'");
        }
    }

    private static void testAppendEmptySubSequence() {
        StringBuilder sb = new StringBuilder("Test");
        CharSequence seq = "Data";
        sb.append(seq, 2, 2); // empty substring
        String result = sb.toString();
        if (!result.equals("Test")) {
            throw new AssertionError("Expected 'Test' but got '" + result + "'");
        }
    }

    private static void testAppendFullSequence() {
        StringBuilder sb = new StringBuilder();
        CharSequence seq = "Complete";
        sb.append(seq, 0, seq.length());
        String result = sb.toString();
        if (!result.equals("Complete")) {
            throw new AssertionError("Expected 'Complete' but got '" + result + "'");
        }
    }

    private static void testAppendNullThrowsNPE() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(null, 0, 1);
            throw new AssertionError("Expected NullPointerException for null CharSequence");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testAppendInvalidIndices() {
        StringBuilder sb = new StringBuilder();
        CharSequence seq = "abc";
        try {
            sb.append(seq, -1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative start index");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
        try {
            sb.append(seq, 1, 5);
            throw new AssertionError("Expected IndexOutOfBoundsException for end index > length");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
        try {
            sb.append(seq, 2, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for start > end");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
