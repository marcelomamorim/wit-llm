/*
 * @test
 * @summary Behavioral tests for StringTokenizer.nextToken() exception and token-sequence contract
 * @run main StringTokenizerWitupTest
 */

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class StringTokenizerWitupTest {

    public static void main(String[] args) {
        testSequentialTokens();
        testEmptyStringThrows();
        testOnlyDelimitersThrows();
        testExhaustionAfterLastToken();
        testReturnDelims();
        testDelimiterChangeOnNextToken();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectNoSuchElement(StringTokenizer st, String context) {
        try {
            String t = st.nextToken();
            throw new RuntimeException(context + ": expected NoSuchElementException but got " + t);
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testSequentialTokens() {
        StringTokenizer st = new StringTokenizer("this is a test");
        check(st.hasMoreTokens(), "tokens expected");
        check(st.nextToken().equals("this"), "token 1");
        check(st.nextToken().equals("is"), "token 2");
        check(st.nextToken().equals("a"), "token 3");
        check(st.nextToken().equals("test"), "token 4");
        check(!st.hasMoreTokens(), "no more tokens expected");
    }

    static void testEmptyStringThrows() {
        StringTokenizer st = new StringTokenizer("");
        check(!st.hasMoreTokens(), "empty string has no tokens");
        expectNoSuchElement(st, "empty string");
    }

    static void testOnlyDelimitersThrows() {
        StringTokenizer st = new StringTokenizer(",,,", ",");
        check(!st.hasMoreTokens(), "only delimiters yield no tokens");
        expectNoSuchElement(st, "only delimiters");
    }

    static void testExhaustionAfterLastToken() {
        StringTokenizer st = new StringTokenizer("x");
        check(st.nextToken().equals("x"), "single token");
        expectNoSuchElement(st, "exhausted tokenizer");
        expectNoSuchElement(st, "exhausted tokenizer repeated");
    }

    static void testReturnDelims() {
        StringTokenizer st = new StringTokenizer("a,b", ",", true);
        check(st.nextToken().equals("a"), "returnDelims token a");
        check(st.nextToken().equals(","), "returnDelims delimiter");
        check(st.nextToken().equals("b"), "returnDelims token b");
        expectNoSuchElement(st, "returnDelims exhausted");
    }

    static void testDelimiterChangeOnNextToken() {
        StringTokenizer st = new StringTokenizer("a;b,c", ";");
        check(st.nextToken().equals("a"), "first token with ;");
        check(st.nextToken(",").equals(";b"), "token after delimiter change");
        check(st.nextToken().equals("c"), "last token");
        expectNoSuchElement(st, "after delimiter change exhausted");
    }
}
