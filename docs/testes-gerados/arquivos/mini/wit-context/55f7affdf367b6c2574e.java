/*
 * @test
 * @summary Test StringTokenizer.nextElement() behavior including NoSuchElementException when no tokens remain
 * @run main StringTokenizerWitupTest
 */

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class StringTokenizerWitupTest {
    public static void main(String[] args) {
        testNextElementReturnsTokens();
        testNextElementThrowsWhenNoMoreTokens();
    }

    private static void testNextElementReturnsTokens() {
        StringTokenizer st = new StringTokenizer("a b c");
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            Object token = st.nextElement();
            if (!(token instanceof String)) {
                throw new AssertionError("Expected token to be a String");
            }
            sb.append((String) token).append(",");
        }
        String result = sb.toString();
        if (!result.equals("a,b,c,")) {
            throw new AssertionError("Tokens mismatch: " + result);
        }
    }

    private static void testNextElementThrowsWhenNoMoreTokens() {
        StringTokenizer st = new StringTokenizer("");
        if (st.hasMoreTokens()) {
            throw new AssertionError("Expected no tokens");
        }
        try {
            st.nextElement();
            throw new AssertionError("Expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
