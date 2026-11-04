/* @test    @summary Test StringTokenizer.nextToken() for normal and exceptional behavior    @run main StringTokenizerWitupTest */

import java.util.StringTokenizer;

public class StringTokenizerWitupTest {

    public static void main(String[] args) {
        testNextTokenNormal();
        testNextTokenException();
    }

    private static void testNextTokenNormal() {
        String str = "a,b,c";
        String delim = ",";
        StringTokenizer tokenizer = new StringTokenizer(str, delim);
        String token1 = tokenizer.nextToken();
        String token2 = tokenizer.nextToken();
        String token3 = tokenizer.nextToken();
        if (!"a".equals(token1)) {
            throw new AssertionError("Expected 'a', got: " + token1);
        }
        if (!"b".equals(token2)) {
            throw new AssertionError("Expected 'b', got: " + token2);
        }
        if (!"c".equals(token3)) {
            throw new AssertionError("Expected 'c', got: " + token3);
        }
    }

    private static void testNextTokenException() {
        String str = "";
        String delim = ",";
        StringTokenizer tokenizer = new StringTokenizer(str, delim);
        // Move position to end to trigger exception
        try {
            while (true) {
                tokenizer.nextToken();
            }
        } catch (java.util.NoSuchElementException e) {
            // Expected exception
            return;
        }
        throw new AssertionError("Expected NoSuchElementException was not thrown");
    }
}
