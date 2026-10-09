/* @test
   @summary Tests the nextToken(String delim) method of StringTokenizer.
   @run main StringTokenizerWitupTest_Pa555148f0ebe
 */
import java.util.*;
public class StringTokenizerWitupTest_Pa555148f0ebe {
    public static void main(String[] args) {
        testNextTokenWithDelimiters();
        testNextTokenWithDifferentDelimiters();
        testNextTokenWithEmptyDelimiters();
        testNextTokenWithNullDelimiters();
    }

    private static void testNextTokenWithDelimiters() {
        String str = "a,b,c";
        String delim = ",";
        StringTokenizer tokenizer = new StringTokenizer(str);
        String token = tokenizer.nextToken(delim);
        if (!"a".equals(token)) {
            throw new AssertionError("Expected 'a', got: " + token);
        }
        token = tokenizer.nextToken(delim);
        if (!"b".equals(token)) {
            throw new AssertionError("Expected 'b', got: " + token);
        }
        token = tokenizer.nextToken(delim);
        if (!"c".equals(token)) {
            throw new AssertionError("Expected 'c', got: " + token);
        }
    }

    private static void testNextTokenWithDifferentDelimiters() {
        String str = "x|y|z";
        String delim = "|";
        StringTokenizer tokenizer = new StringTokenizer(str);
        String token = tokenizer.nextToken(delim);
        if (!"x".equals(token)) {
            throw new AssertionError("Expected 'x', got: " + token);
        }
        token = tokenizer.nextToken(delim);
        if (!"y".equals(token)) {
            throw new AssertionError("Expected 'y', got: " + token);
        }
        token = tokenizer.nextToken(delim);
        if (!"z".equals(token)) {
            throw new AssertionError("Expected 'z', got: " + token);
        }
    }

    private static void testNextTokenWithEmptyDelimiters() {
        String str = "abc";
        String delim = "";
        StringTokenizer tokenizer = new StringTokenizer(str);
        try {
            tokenizer.nextToken(delim);
            throw new AssertionError("Expected IllegalArgumentException for empty delimiter string");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNextTokenWithNullDelimiters() {
        String str = "abc";
        String delim = null;
        StringTokenizer tokenizer = new StringTokenizer(str);
        try {
            tokenizer.nextToken(delim);
            throw new AssertionError("Expected NullPointerException for null delimiter string");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
