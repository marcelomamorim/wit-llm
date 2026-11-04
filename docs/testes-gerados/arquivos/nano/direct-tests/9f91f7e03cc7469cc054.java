/* @test    @summary Test countTokens method of StringTokenizer for various scenarios    @run main StringTokenizerWitupTest */

import java.util.StringTokenizer;

public class StringTokenizerWitupTest {

    public static void main(String[] args) {
        testCountTokensWithDefaultDelimiter();
        testCountTokensWithCustomDelimiter();
        testCountTokensWithReturnDelims();
        testCountTokensEmptyString();
        testCountTokensStringWithOnlyDelimiters();
    }

    static void testCountTokensWithDefaultDelimiter() {
        String str = "one two three";
        StringTokenizer st = new StringTokenizer(str);
        int count = st.countTokens();
        if (count != 3) {
            throw new AssertionError("Expected 3 tokens, but got " + count);
        }
    }

    static void testCountTokensWithCustomDelimiter() {
        String str = "apple,banana,orange";
        StringTokenizer st = new StringTokenizer(str, ",");
        int count = st.countTokens();
        if (count != 3) {
            throw new AssertionError("Expected 3 tokens, but got " + count);
        }
    }

    static void testCountTokensWithReturnDelims() {
        String str = "a,b,c";
        StringTokenizer st = new StringTokenizer(str, ",", true);
        int count = st.countTokens();
        if (count != 5) {
            throw new AssertionError("Expected 5 tokens (including delimiters), but got " + count);
        }
    }

    static void testCountTokensEmptyString() {
        String str = "";
        StringTokenizer st = new StringTokenizer(str);
        int count = st.countTokens();
        if (count != 0) {
            throw new AssertionError("Expected 0 tokens for empty string, but got " + count);
        }
    }

    static void testCountTokensStringWithOnlyDelimiters() {
        String str = ",,,";
        StringTokenizer st = new StringTokenizer(str, ",");
        int count = st.countTokens();
        if (count != 0) {
            throw new AssertionError("Expected 0 tokens for string with only delimiters, but got " + count);
        }
    }
}
