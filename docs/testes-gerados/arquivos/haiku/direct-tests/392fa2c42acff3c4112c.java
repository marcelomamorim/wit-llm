/*
 * @test
 * @summary Verifies StringTokenizer.nextToken(String delim) changes delimiters and returns the next token
 * @run main StringTokenizerWitupTest
 */

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class StringTokenizerWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkEquals(String expected, String actual, String message) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new RuntimeException(message + ": expected [" + expected + "] but was [" + actual + "]");
        }
    }

    static void testReplacesDelimiterForFirstToken() {
        StringTokenizer st = new StringTokenizer("a,b;c", ",");
        checkEquals("a,b", st.nextToken(";"), "nextToken(\";\") first token");
        checkEquals("c", st.nextToken(), "nextToken() after delimiter change");
        check(!st.hasMoreTokens(), "no tokens should remain");
    }

    static void testNewDelimiterAppliesToFollowingTokens() {
        StringTokenizer st = new StringTokenizer("a b c", " ");
        checkEquals("a ", st.nextToken("b"), "first token with delimiter b");
        checkEquals(" c", st.nextToken(), "second token with delimiter b");
        check(!st.hasMoreTokens(), "no tokens should remain after second token");
    }

    static void testEmptyDelimiterReturnsWholeString() {
        StringTokenizer st = new StringTokenizer("ab cd", " ");
        checkEquals("ab cd", st.nextToken(""), "empty delimiter should yield whole string");
        check(!st.hasMoreTokens(), "no tokens should remain after whole string");
    }

    static void testNoMatchingDelimiterReturnsWholeString() {
        StringTokenizer st = new StringTokenizer("abc", ",");
        checkEquals("abc", st.nextToken("x"), "absent delimiter should yield whole string");
        check(!st.hasMoreTokens(), "no tokens should remain");
    }

    static void testExhaustedTokenizerThrows() {
        StringTokenizer st = new StringTokenizer("abc", ",");
        st.nextToken("x");
        try {
            st.nextToken("x");
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
        }
    }

    static void testReturnDelimsFlagPreserved() {
        StringTokenizer st = new StringTokenizer("a,b", ",", true);
        checkEquals("a,b", st.nextToken(";"), "returnDelims=true with absent delimiter");
        check(!st.hasMoreTokens(), "no tokens should remain");
    }

    public static void main(String[] args) {
        testReplacesDelimiterForFirstToken();
        testNewDelimiterAppliesToFollowingTokens();
        testEmptyDelimiterReturnsWholeString();
        testNoMatchingDelimiterReturnsWholeString();
        testExhaustedTokenizerThrows();
        testReturnDelimsFlagPreserved();
    }
}
