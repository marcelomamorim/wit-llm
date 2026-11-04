/* @test    @summary Test StringTokenizer.nextElement() method for correct token retrieval.    @run main StringTokenizerWitupTest */

import java.util.StringTokenizer;

public class StringTokenizerWitupTest {

    public static void main(String[] args) {
        testNextElementWithMultipleTokens();
        testNextElementWithSingleToken();
        testNextElementAfterExhaustion();
    }

    private static void testNextElementWithMultipleTokens() {
        String str = "one two three";
        StringTokenizer tokenizer = new StringTokenizer(str);
        Object token1 = tokenizer.nextElement();
        Object token2 = tokenizer.nextElement();
        Object token3 = tokenizer.nextElement();
        assert token1.equals("one") : "Expected 'one', got " + token1;
        assert token2.equals("two") : "Expected 'two', got " + token2;
        assert token3.equals("three") : "Expected 'three', got " + token3;
    }

    private static void testNextElementWithSingleToken() {
        String str = "single";
        StringTokenizer tokenizer = new StringTokenizer(str);
        Object token = tokenizer.nextElement();
        assert token.equals("single") : "Expected 'single', got " + token;
    }

    private static void testNextElementAfterExhaustion() {
        String str = "a";
        StringTokenizer tokenizer = new StringTokenizer(str);
        Object token1 = tokenizer.nextElement();
        Object token2 = null;
        boolean exceptionThrown = false;
        try {
            token2 = tokenizer.nextElement();
        } catch (Exception e) {
            exceptionThrown = true;
        }
        assert token1.equals("a") : "Expected 'a', got " + token1;
        assert exceptionThrown : "Expected exception when calling nextElement() after exhaustion";
    }
}
