/*
 * @test
 * @summary Verifies StringTokenizer.nextElement() returns tokens as Objects, honors delimiters and returnDelims, and throws NoSuchElementException when exhausted
 * @run main StringTokenizerWitupTest
 */

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class StringTokenizerWitupTest {

    public static void main(String[] args) {
        testDefaultDelimiters();
        testCustomDelimiters();
        testReturnDelims();
        testEmptyStringThrows();
        testMatchesNextToken();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testDefaultDelimiters() {
        StringTokenizer st = new StringTokenizer("this is a test");
        Object o1 = st.nextElement();
        check(o1 instanceof String, "token must be a String");
        check("this".equals(o1), "expected 'this' but got " + o1);
        check("is".equals(st.nextElement()), "expected 'is'");
        check("a".equals(st.nextElement()), "expected 'a'");
        check("test".equals(st.nextElement()), "expected 'test'");
        check(!st.hasMoreElements(), "should have no more elements");
    }

    static void testCustomDelimiters() {
        StringTokenizer st = new StringTokenizer("a,b;c", ",;");
        check("a".equals(st.nextElement()), "expected 'a'");
        check("b".equals(st.nextElement()), "expected 'b'");
        check("c".equals(st.nextElement()), "expected 'c'");
        check(!st.hasMoreElements(), "should have no more elements");
    }

    static void testReturnDelims() {
        StringTokenizer st = new StringTokenizer("a,b", ",", true);
        check("a".equals(st.nextElement()), "expected 'a'");
        check(",".equals(st.nextElement()), "expected delimiter ','");
        check("b".equals(st.nextElement()), "expected 'b'");
        check(!st.hasMoreElements(), "should have no more elements");
    }

    static void testEmptyStringThrows() {
        StringTokenizer st = new StringTokenizer("");
        check(!st.hasMoreElements(), "empty string should have no elements");
        try {
            st.nextElement();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testMatchesNextToken() {
        String input = "  one two  three ";
        StringTokenizer byElement = new StringTokenizer(input);
        StringTokenizer byToken = new StringTokenizer(input);
        while (byToken.hasMoreTokens()) {
            Object element = byElement.nextElement();
            check(byToken.nextToken().equals(element), "nextElement must equal nextToken");
        }
        check(!byElement.hasMoreElements(), "element enumeration should be exhausted");
    }
}
