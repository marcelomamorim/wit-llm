/* @test    @summary Test StringTokenizer.nextToken(String delim) method for correct tokenization behavior    @run main StringTokenizerWitupTest_P3b9d2bc4db74 */

import java.util.StringTokenizer;

public class StringTokenizerWitupTest_P3b9d2bc4db74 {

    public static void main(String[] args) {
        testNextTokenWithDelimiters();
        testNextTokenWithDifferentDelimiters();
        testNextTokenWithEmptyDelimiter();
        testNextTokenWithNullDelimiter();
    }

    private static void testNextTokenWithDelimiters() {
        String str = "apple,banana,orange";
        String delim = ",";
        StringTokenizer tokenizer = new StringTokenizer(str);
        String token = tokenizer.nextToken(delim);
        if (!"apple".equals(token)) {
            throw new AssertionError("Expected 'apple', got: " + token);
        }
        token = tokenizer.nextToken(delim);
        if (!"banana".equals(token)) {
            throw new AssertionError("Expected 'banana', got: " + token);
        }
        token = tokenizer.nextToken(delim);
        if (!"orange".equals(token)) {
            throw new AssertionError("Expected 'orange', got: " + token);
        }
        if (tokenizer.hasMoreTokens()) {
            throw new AssertionError("Expected no more tokens");
        }
    }

    private static void testNextTokenWithDifferentDelimiters() {
        String str = "one|two|three";
        String delim = "|";
        StringTokenizer tokenizer = new StringTokenizer(str);
        String token = tokenizer.nextToken(delim);
        if (!"one".equals(token)) {
            throw new AssertionError("Expected 'one', got: " + token);
        }
        token = tokenizer.nextToken(delim);
        if (!"two".equals(token)) {
            throw new AssertionError("Expected 'two', got: " + token);
        }
        token = tokenizer.nextToken(delim);
        if (!"three".equals(token)) {
            throw new AssertionError("Expected 'three', got: " + token);
        }
        if (tokenizer.hasMoreTokens()) {
            throw new AssertionError("Expected no more tokens");
        }
    }

    private static void testNextTokenWithEmptyDelimiter() {
        String str = "abc";
        String delim = "";
        StringTokenizer tokenizer = new StringTokenizer(str);
        try {
            tokenizer.nextToken(delim);
            throw new AssertionError("Expected exception for empty delimiter");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testNextTokenWithNullDelimiter() {
        String str = "abc";
        String delim = null;
        StringTokenizer tokenizer = new StringTokenizer(str);
        try {
            tokenizer.nextToken(delim);
            throw new AssertionError("Expected exception for null delimiter");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
