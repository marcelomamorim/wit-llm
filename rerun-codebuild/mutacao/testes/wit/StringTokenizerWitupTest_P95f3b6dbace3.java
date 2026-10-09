/* @test    @summary Test for StringTokenizer.nextToken(String delim) method to observe exception behavior.    @run main StringTokenizerWitupTest_P95f3b6dbace3 */

import java.util.StringTokenizer;

public class StringTokenizerWitupTest_P95f3b6dbace3 {

    public static void main(String[] args) {
        testNextTokenWithNullDelimAndExhausted();
        testNextTokenWithValidDelim();
        testNextTokenWithNullDelimAndNotExhausted();
    }

    private static void testNextTokenWithNullDelimAndExhausted() {
        String str = "abc def";
        StringTokenizer st = new StringTokenizer(str);
        // Consume all tokens
        while (st.hasMoreTokens()) {
            st.nextToken();
        }
        try {
            // According to source, calling nextToken with null delim after exhaustion should throw NoSuchElementException
            st.nextToken(null);
            System.out.println("FAIL: Expected NoSuchElementException not thrown in exhausted state with null delim.");
        } catch (java.util.NoSuchElementException e) {
            System.out.println("PASS: Caught expected NoSuchElementException in exhausted state with null delim.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception in exhausted state with null delim: " + e);
        }
    }

    private static void testNextTokenWithValidDelim() {
        String str = "one,two,three";
        StringTokenizer st = new StringTokenizer(str, ",");
        try {
            String token1 = st.nextToken();
            String token2 = st.nextToken();
            String token3 = st.nextToken();
            if ("one".equals(token1) && "two".equals(token2) && "three".equals(token3)) {
                System.out.println("PASS: Tokens match expected values.");
            } else {
                System.out.println("FAIL: Tokens do not match expected values.");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception in valid delim test: " + e);
        }
    }

    private static void testNextTokenWithNullDelimAndNotExhausted() {
        String str = "abc def";
        StringTokenizer st = new StringTokenizer(str);
        try {
            String token1 = st.nextToken();
            String token2 = st.nextToken();
            // Now call with null delim, should throw exception if exhausted
            st.nextToken(null);
            System.out.println("FAIL: Expected NoSuchElementException not thrown in not exhausted state with null delim.");
        } catch (java.util.NoSuchElementException e) {
            System.out.println("PASS: Caught expected NoSuchElementException in not exhausted state with null delim.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception in not exhausted state with null delim: " + e);
        }
    }
}
