/*
 * @test
 * @summary Test StringTokenizer.nextToken(String delim) for normal and boundary cases
 * @run main StringTokenizerWitupTest
 */

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class StringTokenizerWitupTest {
    public static void main(String[] args) {
        testNextTokenWithNewDelim();
        testNextTokenWithNullDelimAndNoMoreTokens();
        testNextTokenWithNullDelimAndHasMoreTokens();
    }

    private static void testNextTokenWithNewDelim() {
        StringTokenizer st = new StringTokenizer("a,b;c", ",;");
        // Change delimiter to space and get next token
        String token = st.nextToken(" ");
        if (!"a,b;c".equals(token)) {
            throw new AssertionError("Expected token 'a,b;c' but got '" + token + "'");
        }
    }

    private static void testNextTokenWithNullDelimAndNoMoreTokens() {
        StringTokenizer st = new StringTokenizer("");
        // Exhaust tokens
        boolean hasMore = st.hasMoreTokens();
        if (hasMore) {
            throw new AssertionError("Expected no tokens but hasMoreTokens() returned true");
        }
        try {
            // According to WIT expath, nextToken(null) when no tokens should throw NoSuchElementException
            st.nextToken(null);
            throw new AssertionError("Expected NoSuchElementException when calling nextToken(null) with no tokens");
        } catch (NoSuchElementException e) {
            // Expected
        }
    }

    private static void testNextTokenWithNullDelimAndHasMoreTokens() {
        StringTokenizer st = new StringTokenizer("token1 token2");
        // nextToken(null) should behave like nextToken() and return next token
        String token1 = st.nextToken(null);
        if (!"token1".equals(token1)) {
            throw new AssertionError("Expected 'token1' but got '" + token1 + "'");
        }
        String token2 = st.nextToken(null);
        if (!"token2".equals(token2)) {
            throw new AssertionError("Expected 'token2' but got '" + token2 + "'");
        }
        if (st.hasMoreTokens()) {
            throw new AssertionError("Expected no more tokens after consuming all");
        }
    }
}
