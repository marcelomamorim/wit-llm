/*
 * @test
 * @summary Verify StringTokenizer.countTokens() returns the number of remaining tokens without consuming them
 * @run main StringTokenizerWitupTest
 */

import java.util.StringTokenizer;

public class StringTokenizerWitupTest {

    static int failures = 0;

    static void check(String name, int expected, int actual) {
        if (expected != actual) {
            failures++;
            System.err.println("FAIL: " + name + " expected=" + expected + " actual=" + actual);
        }
    }

    public static void main(String[] args) {
        // Default delimiters, returnDelims=false
        check("default delims sentence", 4, new StringTokenizer("this is a test").countTokens());
        check("empty string", 0, new StringTokenizer("").countTokens());
        check("only delimiters", 0, new StringTokenizer("   ").countTokens());
        check("single token", 1, new StringTokenizer("abc").countTokens());

        // Custom delimiter, consecutive delimiters collapse
        check("consecutive delimiters", 3, new StringTokenizer("a,b,,c", ",").countTokens());
        check("leading and trailing delimiters", 2, new StringTokenizer(",a,b,", ",").countTokens());

        // Empty delimiter set: whole string is one token
        check("empty delimiter set", 1, new StringTokenizer("abc", "").countTokens());

        // returnDelims=true: delimiters count as tokens
        check("returnDelims simple", 3, new StringTokenizer("a,b", ",", true).countTokens());
        check("returnDelims consecutive", 2, new StringTokenizer(",,", ",", true).countTokens());
        check("returnDelims spaces", 2, new StringTokenizer("  ", " ", true).countTokens());
        check("returnDelims mixed", 5, new StringTokenizer("a,,b", ",", true).countTokens());

        // countTokens must not consume tokens: repeated calls are stable
        StringTokenizer st = new StringTokenizer("x y z");
        check("first count", 3, st.countTokens());
        check("second count stable", 3, st.countTokens());

        // After consuming a token, the count reflects remaining tokens
        st.nextToken();
        check("after one nextToken", 2, st.countTokens());
        st.nextToken();
        check("after two nextToken", 1, st.countTokens());
        st.nextToken();
        check("after all tokens", 0, st.countTokens());

        // Count agrees with the number of nextToken() calls possible
        StringTokenizer st2 = new StringTokenizer("  one two,three  ", " ,");
        int expected = st2.countTokens();
        int actual = 0;
        while (st2.hasMoreTokens()) {
            st2.nextToken();
            actual++;
        }
        check("count matches iteration", expected, actual);

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
