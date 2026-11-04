/*
 * @test
 * @summary Verifies observable behavior of StringTokenizer.hasMoreElements()
 * @run main StringTokenizerWitupTest
 */

public class StringTokenizerWitupTest {

    public static void main(String[] args) {
        testEmptyStringHasNoElements();
        testDefaultDelimitersEnumerateTokens();
        testOnlyDelimitersHasNoElementsWhenNotReturningDelims();
        testReturnDelimsExposesDelimiterElements();
        testHasMoreElementsDoesNotAdvancePosition();
        testHasMoreElementsAgreesWithHasMoreTokens();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyStringHasNoElements() {
        StringTokenizer st = new StringTokenizer("");
        check(!st.hasMoreElements(), "empty string should have no elements");
    }

    static void testDefaultDelimitersEnumerateTokens() {
        StringTokenizer st = new StringTokenizer("a b c");
        check(st.hasMoreElements(), "expected element 1");
        check("a".equals(st.nextElement()), "expected a");
        check(st.hasMoreElements(), "expected element 2");
        check("b".equals(st.nextElement()), "expected b");
        check(st.hasMoreElements(), "expected element 3");
        check("c".equals(st.nextElement()), "expected c");
        check(!st.hasMoreElements(), "expected no more elements after c");
    }

    static void testOnlyDelimitersHasNoElementsWhenNotReturningDelims() {
        StringTokenizer st = new StringTokenizer(",,,", ",");
        check(!st.hasMoreElements(), "only delimiters without returnDelims should yield no elements");

        StringTokenizer ws = new StringTokenizer("   ");
        check(!ws.hasMoreElements(), "only whitespace with default delimiters should yield no elements");
    }

    static void testReturnDelimsExposesDelimiterElements() {
        StringTokenizer st = new StringTokenizer(",,", ",", true);
        check(st.hasMoreElements(), "returnDelims=true should expose delimiter as element");
        check(",".equals(st.nextElement()), "first element should be delimiter");
        check(st.hasMoreElements(), "second delimiter should be an element");
        check(",".equals(st.nextElement()), "second element should be delimiter");
        check(!st.hasMoreElements(), "no elements should remain");
    }

    static void testHasMoreElementsDoesNotAdvancePosition() {
        StringTokenizer st = new StringTokenizer("x");
        check(st.hasMoreElements(), "first call should be true");
        check(st.hasMoreElements(), "second call should still be true");
        check(st.countTokens() == 1, "token count should remain 1");
        check("x".equals(st.nextToken()), "token should still be x");
        check(!st.hasMoreElements(), "no elements after consuming x");
    }

    static void testHasMoreElementsAgreesWithHasMoreTokens() {
        StringTokenizer st = new StringTokenizer("a,b", ",");
        while (true) {
            boolean tokens = st.hasMoreTokens();
            boolean elements = st.hasMoreElements();
            check(tokens == elements, "hasMoreElements must equal hasMoreTokens");
            if (!tokens) {
                break;
            }
            st.nextToken();
        }
    }
}
