/*
 * @test
 * @summary Test StringTokenizer.nextToken(String delim) behavior
 * @run main StringTokenizerWitupTest_P704cbddcd5d2
 */

import java.util.StringTokenizer;

public class StringTokenizerWitupTest_P704cbddcd5d2 {
    public static void main(String[] args) {
        testNextTokenWithNewDelimiters();
        testNextTokenWithEmptyDelimiters();
        testNextTokenWithNullDelimiters();
    }

    private static void testNextTokenWithNewDelimiters() {
        StringTokenizer st = new StringTokenizer("a,b;c", ",;");
        // Change delimiters to comma only
        String token1 = st.nextToken(",");
        if (!"a".equals(token1)) {
            throw new Error("Expected token 'a' but got '" + token1 + "'");
        }
        String token2 = st.nextToken();
        if (!"b;c".equals(token2)) {
            throw new Error("Expected token 'b;c' but got '" + token2 + "'");
        }
    }

    private static void testNextTokenWithEmptyDelimiters() {
        StringTokenizer st = new StringTokenizer("abc");
        // Set delimiters to empty string, so no delimiters
        String token1 = st.nextToken("");
        if (!"abc".equals(token1)) {
            throw new Error("Expected token 'abc' but got '" + token1 + "'");
        }
        // After consuming all tokens, hasMoreTokens should be false
        if (st.hasMoreTokens()) {
            throw new Error("Expected no more tokens after consuming all");
        }
    }

    private static void testNextTokenWithNullDelimiters() {
        StringTokenizer st = new StringTokenizer("a b c");
        try {
            st.nextToken(null);
            throw new Error("Expected NullPointerException for null delimiter");
        } catch (NullPointerException e) {
            // Expected
        }
    }
}
