/* @test    @summary Test for StringTokenizer.nextElement() method to verify behavior and exception handling    @run main StringTokenizerWitupTest */

import java.util.StringTokenizer;

public class StringTokenizerWitupTest {

    public static void main(String[] args) {
        testNextElementNormal();
        testNextElementBeyondEnd();
    }

    private static void testNextElementNormal() {
        String str = "a b c";
        StringTokenizer tokenizer = new StringTokenizer(str);
        try {
            Object token1 = tokenizer.nextElement();
            Object token2 = tokenizer.nextElement();
            Object token3 = tokenizer.nextElement();
            if (!("a".equals(token1) && "b".equals(token2) && "c".equals(token3))) {
                throw new AssertionError("Tokens do not match expected values");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in normal case", e);
        }
    }

    private static void testNextElementBeyondEnd() {
        String str = "x";
        StringTokenizer tokenizer = new StringTokenizer(str);
        try {
            tokenizer.nextElement();
            // Next call should throw NoSuchElementException
            tokenizer.nextElement();
            throw new AssertionError("Expected NoSuchElementException was not thrown");
        } catch (java.util.NoSuchElementException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
