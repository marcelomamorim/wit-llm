/* @test    @summary Test hasMoreElements() method of StringTokenizer for correct boolean return based on tokens availability    @run main StringTokenizerWitupTest */

import java.util.StringTokenizer;

public class StringTokenizerWitupTest {

    public static void main(String[] args) {
        testHasMoreElementsWithTokens();
        testHasMoreElementsWithoutTokens();
    }

    static void testHasMoreElementsWithTokens() {
        String str = "a b c";
        String delim = " ";
        StringTokenizer tokenizer = new StringTokenizer(str, delim);
        boolean result = tokenizer.hasMoreElements();
        if (!result) {
            throw new AssertionError("Expected hasMoreElements() to return true when tokens are available");
        }
    }

    static void testHasMoreElementsWithoutTokens() {
        String str = "";
        String delim = " ";
        StringTokenizer tokenizer = new StringTokenizer(str, delim);
        // Consume all tokens (none in this case)
        while (tokenizer.hasMoreTokens()) {
            tokenizer.nextToken();
        }
        boolean result = tokenizer.hasMoreElements();
        if (result) {
            throw new AssertionError("Expected hasMoreElements() to return false when no tokens are available");
        }
    }
}
