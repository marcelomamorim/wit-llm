/*
 * @test
 * @summary Test java.util.StringTokenizer.nextElement() method
 * @run main StringTokenizerWitupTest
 */

import java.util.StringTokenizer;
import java.util.NoSuchElementException;

public class StringTokenizerWitupTest {
    public static void main(String[] args) {
        testNextElementReturnsTokens();
        testNextElementThrowsWhenNoMoreTokens();
    }

    private static void testNextElementReturnsTokens() {
        StringTokenizer st = new StringTokenizer("a,b,c", ",");
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreElements()) {
            Object token = st.nextElement();
            if (!(token instanceof String)) {
                throw new AssertionError("Expected token to be a String");
            }
            sb.append((String) token);
        }
        if (!"abc".equals(sb.toString())) {
            throw new AssertionError("Tokens concatenated do not match expected 'abc', got: " + sb.toString());
        }
    }

    private static void testNextElementThrowsWhenNoMoreTokens() {
        StringTokenizer st = new StringTokenizer("");
        boolean threw = false;
        try {
            st.nextElement();
        } catch (NoSuchElementException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NoSuchElementException when calling nextElement() with no tokens");
        }
    }
}
