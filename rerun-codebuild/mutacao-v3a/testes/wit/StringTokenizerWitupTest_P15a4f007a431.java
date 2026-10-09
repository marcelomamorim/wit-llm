/*
 * @test
 * @summary Tests for java.util.StringTokenizer class, focusing on nextToken(String delim)
 * @run main StringTokenizerWitupTest_P15a4f007a431
 */

import java.util.StringTokenizer;

public class StringTokenizerWitupTest_P15a4f007a431 {

    public static void main(String[] args) {
        testNextTokenWithNullDelimiterThrows();
        testNextTokenWithValidDelimiterReturnsToken();
        testNextTokenAfterExhaustionThrows();
    }

    static void testNextTokenWithNullDelimiterThrows() {
        StringTokenizer tokenizer = new StringTokenizer("a b c");
        try {
            // Expecting NoSuchElementException when calling nextToken() after exhaustion
            while (true) {
                tokenizer.nextToken();
            }
        } catch (java.util.NoSuchElementException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception", e);
        }
        StringTokenizer tokenizer2 = new StringTokenizer("a b c");
        // Consume all tokens
        while (tokenizer2.hasMoreTokens()) {
            tokenizer2.nextToken();
        }
        // Now calling with null delimiter should throw
        try {
            tokenizer2.nextToken((String) null);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception", e);
        }
    }

    static void testNextTokenWithValidDelimiterReturnsToken() {
        StringTokenizer tokenizer = new StringTokenizer("a,b,c", ",");
        String token = tokenizer.nextToken(",");
        if (token == null || !token.equals("a")) {
            throw new AssertionError("Expected token 'a', got: " + token);
        }
        token = tokenizer.nextToken(",");
        if (token == null || !token.equals("b")) {
            throw new AssertionError("Expected token 'b', got: " + token);
        }
    }

    static void testNextTokenAfterExhaustionThrows() {
        StringTokenizer tokenizer = new StringTokenizer("x");
        tokenizer.nextToken(); // consumes 'x'
        if (tokenizer.hasMoreTokens()) {
            throw new AssertionError("Tokenizer should have no more tokens");
        }
        try {
            tokenizer.nextToken();
            throw new AssertionError("Expected NoSuchElementException was not thrown");
        } catch (java.util.NoSuchElementException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception", e);
        }
    }
}
