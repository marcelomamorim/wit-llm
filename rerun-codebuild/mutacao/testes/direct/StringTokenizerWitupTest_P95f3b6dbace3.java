/* @test    @summary Test StringTokenizer.nextToken(String delim) method for correct tokenization behavior.    @run main StringTokenizerWitupTest_P95f3b6dbace3 */

import java.util.StringTokenizer;

public class StringTokenizerWitupTest_P95f3b6dbace3 {

    public static void main(String[] args) {
        testNextTokenWithDelimiters();
        testNextTokenWithDifferentDelimiters();
        testNextTokenWithEmptyDelimiters();
        testNextTokenWithNullDelimiters();
    }

    private static void testNextTokenWithDelimiters() {
        String str = "apple,banana,orange";
        String delim = ",";
        StringTokenizer tokenizer = new StringTokenizer(str);
        String token = tokenizer.nextToken(delim);
        assert token.equals("apple") : "Expected 'apple', got '" + token + "'";
        token = tokenizer.nextToken(delim);
        assert token.equals("banana") : "Expected 'banana', got '" + token + "'";
        token = tokenizer.nextToken(delim);
        assert token.equals("orange") : "Expected 'orange', got '" + token + "'";
    }

    private static void testNextTokenWithDifferentDelimiters() {
        String str = "one|two|three";
        String delim = "|";
        StringTokenizer tokenizer = new StringTokenizer(str);
        String token = tokenizer.nextToken(delim);
        assert token.equals("one") : "Expected 'one', got '" + token + "'";
        token = tokenizer.nextToken(delim);
        assert token.equals("two") : "Expected 'two', got '" + token + "'";
        token = tokenizer.nextToken(delim);
        assert token.equals("three") : "Expected 'three', got '" + token + "'";
    }

    private static void testNextTokenWithEmptyDelimiters() {
        String str = "abc";
        String delim = "";
        StringTokenizer tokenizer = new StringTokenizer(str);
        String token = tokenizer.nextToken(delim);
        assert token.equals("abc") : "Expected 'abc', got '" + token + "'";
    }

    private static void testNextTokenWithNullDelimiters() {
        String str = "hello world";
        String delim = null;
        StringTokenizer tokenizer = new StringTokenizer(str);
        String token = tokenizer.nextToken(delim);
        assert token.equals("hello") : "Expected 'hello', got '" + token + "'";
        token = tokenizer.nextToken(delim);
        assert token.equals("world") : "Expected 'world', got '" + token + "'";
    }
}
