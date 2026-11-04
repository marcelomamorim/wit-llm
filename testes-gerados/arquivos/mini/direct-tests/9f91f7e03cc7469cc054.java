/*
 * @test
 * @summary Test StringTokenizer.countTokens() method behavior
 * @run main StringTokenizerWitupTest
 */

import java.util.StringTokenizer;

public class StringTokenizerWitupTest {
    public static void main(String[] args) {
        testEmptyString();
        testSimpleTokens();
        testDelimitersOnly();
        testReturnDelimsTrue();
        testMultipleDelimiters();
    }

    private static void testEmptyString() {
        StringTokenizer st = new StringTokenizer("");
        int count = st.countTokens();
        if (count != 0) {
            throw new RuntimeException("Expected 0 tokens for empty string, got " + count);
        }
    }

    private static void testSimpleTokens() {
        StringTokenizer st = new StringTokenizer("a b c");
        int count = st.countTokens();
        if (count != 3) {
            throw new RuntimeException("Expected 3 tokens for 'a b c', got " + count);
        }
    }

    private static void testDelimitersOnly() {
        StringTokenizer st = new StringTokenizer("   ");
        int count = st.countTokens();
        if (count != 0) {
            throw new RuntimeException("Expected 0 tokens for string with only delimiters, got " + count);
        }
    }

    private static void testReturnDelimsTrue() {
        StringTokenizer st = new StringTokenizer("a,b,c", ",", true);
        int count = st.countTokens();
        // Tokens: a , b , c  => 5 tokens
        if (count != 5) {
            throw new RuntimeException("Expected 5 tokens for 'a,b,c' with returnDelims=true, got " + count);
        }
    }

    private static void testMultipleDelimiters() {
        StringTokenizer st = new StringTokenizer("a,b;c", ",;");
        int count = st.countTokens();
        if (count != 3) {
            throw new RuntimeException("Expected 3 tokens for 'a,b;c' with delimiters ',;' got " + count);
        }
    }
}
