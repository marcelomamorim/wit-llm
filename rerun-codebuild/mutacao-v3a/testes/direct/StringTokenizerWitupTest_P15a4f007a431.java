/* @test    @summary Test StringTokenizer.nextToken with custom delimiter    @run main StringTokenizerWitupTest_P15a4f007a431 */

import java.util.StringTokenizer;

public class StringTokenizerWitupTest_P15a4f007a431 {

    public static void main(String[] args) {
        testNextTokenWithCustomDelimiter();
        testNextTokenWithDifferentDelimiter();
    }

    static void testNextTokenWithCustomDelimiter() {
        String str = "apple,banana,orange";
        String delimiters = ",";
        StringTokenizer tokenizer = new StringTokenizer(str, delimiters);
        String token1 = tokenizer.nextToken(",");
        String token2 = tokenizer.nextToken(",");
        String token3 = tokenizer.nextToken(",");
        if (!"apple".equals(token1)) {
            throw new AssertionError("Expected 'apple', got '" + token1 + "'");
        }
        if (!"banana".equals(token2)) {
            throw new AssertionError("Expected 'banana', got '" + token2 + "'");
        }
        if (!"orange".equals(token3)) {
            throw new AssertionError("Expected 'orange', got '" + token3 + "'");
        }
    }

    static void testNextTokenWithDifferentDelimiter() {
        String str = "one|two|three";
        String delimiters = "|";
        StringTokenizer tokenizer = new StringTokenizer(str, delimiters);
        String token1 = tokenizer.nextToken("|");
        String token2 = tokenizer.nextToken("|");
        String token3 = tokenizer.nextToken("|");
        if (!"one".equals(token1)) {
            throw new AssertionError("Expected 'one', got '" + token1 + "'");
        }
        if (!"two".equals(token2)) {
            throw new AssertionError("Expected 'two', got '" + token2 + "'");
        }
        if (!"three".equals(token3)) {
            throw new AssertionError("Expected 'three', got '" + token3 + "'");
        }
    }
}
