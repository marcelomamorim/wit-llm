/*
 * @test
 * @summary Black-box tests for StringTokenizer.nextToken covering default and custom delimiters, returnDelims behavior and NoSuchElementException when no tokens remain
 * @run main StringTokenizerWitupTest
 */

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class StringTokenizerWitupTest {

    public static void main(String[] args) {
        testDefaultDelimiters();
        testCustomDelimiters();
        testReturnDelimsTrue();
        testReturnDelimsOnlyDelimiters();
        testTrailingDelimiterNotReturned();
        testEmptyStringThrows();
        testOnlyDelimitersThrows();
    }

    static void testDefaultDelimiters() {
        StringTokenizer st = new StringTokenizer("  this is  a test ");
        expectEquals(st.hasMoreTokens(), true, "hasMoreTokens before first token");
        expectToken(st, "this");
        expectToken(st, "is");
        expectToken(st, "a");
        expectToken(st, "test");
        expectEquals(st.hasMoreTokens(), false, "hasMoreTokens after last token");
        expectNoSuchElement(st);
    }

    static void testCustomDelimiters() {
        StringTokenizer st = new StringTokenizer("a,b;;c", ",;");
        expectToken(st, "a");
        expectToken(st, "b");
        expectToken(st, "c");
        expectNoSuchElement(st);
    }

    static void testReturnDelimsTrue() {
        StringTokenizer st = new StringTokenizer("a,,b", ",", true);
        expectToken(st, "a");
        expectToken(st, ",");
        expectToken(st, ",");
        expectToken(st, "b");
        expectNoSuchElement(st);
    }

    static void testReturnDelimsOnlyDelimiters() {
        StringTokenizer st = new StringTokenizer(",;", ",;", true);
        expectToken(st, ",");
        expectToken(st, ";");
        expectNoSuchElement(st);
    }

    static void testTrailingDelimiterNotReturned() {
        StringTokenizer st = new StringTokenizer("x,y,", ",");
        expectToken(st, "x");
        expectToken(st, "y");
        expectEquals(st.hasMoreTokens(), false, "no token after trailing delimiter");
        expectNoSuchElement(st);
    }

    static void testEmptyStringThrows() {
        StringTokenizer st = new StringTokenizer("");
        expectEquals(st.hasMoreTokens(), false, "empty string has no tokens");
        expectNoSuchElement(st);
    }

    static void testOnlyDelimitersThrows() {
        StringTokenizer st = new StringTokenizer("   ");
        expectEquals(st.hasMoreTokens(), false, "only delimiters yield no tokens");
        expectNoSuchElement(st);
    }

    static void expectToken(StringTokenizer st, String expected) {
        String actual = st.nextToken();
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected token [" + expected + "] but got [" + actual + "]");
        }
    }

    static void expectNoSuchElement(StringTokenizer st) {
        try {
            String tok = st.nextToken();
            throw new RuntimeException("Expected NoSuchElementException but got token [" + tok + "]");
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    static void expectEquals(boolean actual, boolean expected, String what) {
        if (actual != expected) {
            throw new RuntimeException(what + ": expected " + expected + " but got " + actual);
        }
    }
}
