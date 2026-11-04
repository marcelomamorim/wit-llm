/*
 * @test
 * @summary Tests the hasMoreTokens method of StringTokenizer for normal and null delimiter scenarios.
 * @run main StringTokenizerWitupTest
 */

import java.util.StringTokenizer;

public class StringTokenizerWitupTest {

    public static void main(String[] args) {
        testHasMoreTokensWithValidDelimiters();
        testHasMoreTokensWithNullDelimiters();
    }

    private static void testHasMoreTokensWithValidDelimiters() {
        String str = "a,b,c";
        String delim = ",";
        StringTokenizer tokenizer = new StringTokenizer(str, delim);
        boolean result = tokenizer.hasMoreTokens();
        if (!result) {
            throw new AssertionError("Expected hasMoreTokens() to be true for non-empty string.");
        }
    }

    private static void testHasMoreTokensWithNullDelimiters() {
        String str = "abc";
        String delim = null;
        try {
            StringTokenizer tokenizer = new StringTokenizer(str, delim);
            boolean result = tokenizer.hasMoreTokens();
            throw new AssertionError("Expected NullPointerException when delimiters are null.");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
