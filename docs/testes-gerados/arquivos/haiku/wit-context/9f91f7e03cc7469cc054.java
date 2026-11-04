/*
 * @test
 * @summary White-box-free behavioral checks for StringTokenizer.countTokens()
 * @run main StringTokenizerWitupTest
 */

import java.util.StringTokenizer;

public class StringTokenizerWitupTest {

    public static void main(String[] args) {
        checkDefaultDelimiters();
        checkEmptyString();
        checkOnlyDelimiters();
        checkReturnDelims();
        checkAfterNextToken();
        checkNullDelimitersWithPendingInput();
        checkNullDelimitersEmptyInput();
    }

    static void checkDefaultDelimiters() {
        StringTokenizer st = new StringTokenizer("a b  c");
        check(st.countTokens() == 3, "expected 3 tokens");
    }

    static void checkEmptyString() {
        StringTokenizer st = new StringTokenizer("");
        check(st.countTokens() == 0, "expected 0 tokens for empty input");
    }

    static void checkOnlyDelimiters() {
        StringTokenizer st = new StringTokenizer("   ", " ");
        check(st.countTokens() == 0, "expected 0 tokens for only delimiters");
    }

    static void checkReturnDelims() {
        StringTokenizer st = new StringTokenizer("a b", " ", true);
        check(st.countTokens() == 3, "expected 3 tokens including delimiter");
    }

    static void checkAfterNextToken() {
        StringTokenizer st = new StringTokenizer("a b c");
        st.nextToken();
        check(st.countTokens() == 2, "expected 2 remaining tokens");
    }

    static void checkNullDelimitersWithPendingInput() {
        StringTokenizer st = new StringTokenizer("abc", null);
        boolean thrown = false;
        try {
            st.countTokens();
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null delimiters");
    }

    static void checkNullDelimitersEmptyInput() {
        StringTokenizer st = new StringTokenizer("", null);
        check(st.countTokens() == 0, "expected 0 tokens when no input remains");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
