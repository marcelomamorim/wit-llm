/* @test    @summary Test for StringTokenizer.hasMoreTokens() method behavior    @run main StringTokenizerWitupTest */

import java.util.StringTokenizer;

public class StringTokenizerWitupTest {

    public static void main(String[] args) {
        testHasMoreTokensWithRemainingTokens();
        testHasMoreTokensAtEnd();
        testHasMoreTokensWithEmptyString();
    }

    static void testHasMoreTokensWithRemainingTokens() {
        String str = "a,b,c";
        String delim = ",";
        StringTokenizer tokenizer = new StringTokenizer(str, delim);
        boolean result = tokenizer.hasMoreTokens();
        if (!result) {
            throw new AssertionError("Expected true, but got false for hasMoreTokens() with remaining tokens");
        }
    }

    static void testHasMoreTokensAtEnd() {
        String str = "a";
        String delim = ",";
        StringTokenizer tokenizer = new StringTokenizer(str, delim);
        tokenizer.nextToken();
        boolean result = tokenizer.hasMoreTokens();
        if (result) {
            throw new AssertionError("Expected false, but got true at end of tokens");
        }
    }

    static void testHasMoreTokensWithEmptyString() {
        String str = "";
        String delim = ",";
        StringTokenizer tokenizer = new StringTokenizer(str, delim);
        boolean result = tokenizer.hasMoreTokens();
        if (result) {
            throw new AssertionError("Expected false, but got true for empty string");
        }
    }
}
