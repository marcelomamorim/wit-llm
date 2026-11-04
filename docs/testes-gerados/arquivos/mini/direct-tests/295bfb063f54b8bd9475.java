/*
 * @test
 * @summary Test java.util.StringTokenizer.hasMoreElements() method
 * @run main StringTokenizerWitupTest
 */

import java.util.StringTokenizer;

public class StringTokenizerWitupTest {
    public static void main(String[] args) {
        testHasMoreElementsWithTokens();
        testHasMoreElementsEmptyString();
        testHasMoreElementsOnlyDelimiters();
        testHasMoreElementsReturnDelimsTrue();
    }

    private static void testHasMoreElementsWithTokens() {
        StringTokenizer st = new StringTokenizer("a b c");
        int count = 0;
        while (st.hasMoreElements()) {
            Object token = st.nextElement();
            if (!(token instanceof String)) {
                throw new RuntimeException("Token is not a String");
            }
            count++;
        }
        if (count != 3) {
            throw new RuntimeException("Expected 3 tokens, got " + count);
        }
    }

    private static void testHasMoreElementsEmptyString() {
        StringTokenizer st = new StringTokenizer("");
        if (st.hasMoreElements()) {
            throw new RuntimeException("Expected no elements for empty string");
        }
    }

    private static void testHasMoreElementsOnlyDelimiters() {
        StringTokenizer st = new StringTokenizer(",,,", ",");
        if (st.hasMoreElements()) {
            throw new RuntimeException("Expected no elements when only delimiters and returnDelims=false");
        }
    }

    private static void testHasMoreElementsReturnDelimsTrue() {
        StringTokenizer st = new StringTokenizer(",,,", ",", true);
        int count = 0;
        while (st.hasMoreElements()) {
            Object token = st.nextElement();
            if (!(token instanceof String)) {
                throw new RuntimeException("Token is not a String");
            }
            if (!token.equals(",")) {
                throw new RuntimeException("Expected delimiter token ',', got: " + token);
            }
            count++;
        }
        if (count != 3) {
            throw new RuntimeException("Expected 3 delimiter tokens, got " + count);
        }
    }
}
