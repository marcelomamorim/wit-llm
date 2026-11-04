/*
 * @test
 * @summary Test StringTokenizer.hasMoreTokens() behavior
 * @run main StringTokenizerWitupTest
 */

import java.util.StringTokenizer;

public class StringTokenizerWitupTest {
    public static void main(String[] args) {
        testHasMoreTokensWithDefaultDelimiters();
        testHasMoreTokensWithCustomDelimiters();
        testHasMoreTokensWithReturnDelimsTrue();
        testHasMoreTokensEmptyString();
        testHasMoreTokensOnlyDelimiters();
    }

    static void testHasMoreTokensWithDefaultDelimiters() {
        StringTokenizer st = new StringTokenizer("this is a test");
        int count = 0;
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token == null || token.isEmpty()) {
                throw new RuntimeException("Token should not be null or empty");
            }
            count++;
        }
        if (count != 4) {
            throw new RuntimeException("Expected 4 tokens but got " + count);
        }
    }

    static void testHasMoreTokensWithCustomDelimiters() {
        StringTokenizer st = new StringTokenizer("a,b;c", ",;");
        String[] expected = {"a", "b", "c"};
        int i = 0;
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (!token.equals(expected[i])) {
                throw new RuntimeException("Expected token '" + expected[i] + "' but got '" + token + "'");
            }
            i++;
        }
        if (i != expected.length) {
            throw new RuntimeException("Expected " + expected.length + " tokens but got " + i);
        }
    }

    static void testHasMoreTokensWithReturnDelimsTrue() {
        StringTokenizer st = new StringTokenizer("a,b;c", ",;", true);
        String[] expected = {"a", ",", "b", ";", "c"};
        int i = 0;
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (!token.equals(expected[i])) {
                throw new RuntimeException("Expected token '" + expected[i] + "' but got '" + token + "'");
            }
            i++;
        }
        if (i != expected.length) {
            throw new RuntimeException("Expected " + expected.length + " tokens but got " + i);
        }
    }

    static void testHasMoreTokensEmptyString() {
        StringTokenizer st = new StringTokenizer("");
        if (st.hasMoreTokens()) {
            throw new RuntimeException("Expected no tokens for empty string");
        }
    }

    static void testHasMoreTokensOnlyDelimiters() {
        StringTokenizer st = new StringTokenizer(",,,", ",");
        if (!st.hasMoreTokens()) {
            throw new RuntimeException("Expected tokens when delimiters are present but returnDelims is false");
        }
        // The tokens should be empty strings between delimiters
        int count = 0;
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token.isEmpty()) {
                throw new RuntimeException("Token should not be empty");
            }
            count++;
        }
        if (count != 0) {
            // Actually with returnDelims false, delimiters are skipped, so no tokens expected
            // So count should be zero
            throw new RuntimeException("Expected 0 tokens but got " + count);
        }
    }
}
