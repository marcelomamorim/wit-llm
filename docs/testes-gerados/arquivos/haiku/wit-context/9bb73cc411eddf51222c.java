/*
 * @test
 * @summary Behavioral checks for StringTokenizer.hasMoreTokens()
 * @run main StringTokenizerWitupTest
 */

import java.util.StringTokenizer;

public class StringTokenizerWitupTest {
    public static void main(String[] args) {
        check(!new StringTokenizer("").hasMoreTokens(), "empty string has no tokens");
        check(new StringTokenizer("this is a test").hasMoreTokens(), "non-empty string has tokens");

        StringTokenizer st = new StringTokenizer("a b");
        check(st.hasMoreTokens(), "first check");
        check(st.hasMoreTokens(), "hasMoreTokens must not consume tokens");
        check("a".equals(st.nextToken()), "first token is a");
        check(st.hasMoreTokens(), "second token remains");
        check("b".equals(st.nextToken()), "second token is b");
        check(!st.hasMoreTokens(), "exhausted tokenizer");
        check(!st.hasMoreTokens(), "still exhausted");

        st = new StringTokenizer("   ", " ");
        check(!st.hasMoreTokens(), "only delimiters yields no tokens");

        st = new StringTokenizer("a,b", ",", true);
        check(st.hasMoreTokens(), "returnDelims: first token");
        check("a".equals(st.nextToken()), "returnDelims: a");
        check(st.hasMoreTokens(), "returnDelims: delimiter remains");
        check(",".equals(st.nextToken()), "returnDelims: comma returned");
        check(st.hasMoreTokens(), "returnDelims: b remains");
        check("b".equals(st.nextToken()), "returnDelims: b");
        check(!st.hasMoreTokens(), "returnDelims: exhausted");

        st = new StringTokenizer(",,", ",", true);
        check(st.hasMoreTokens(), "only delimiters with returnDelims");
        check(",".equals(st.nextToken()), "first delimiter");
        check(st.hasMoreTokens(), "second delimiter remains");
        check(",".equals(st.nextToken()), "second delimiter");
        check(!st.hasMoreTokens(), "no more delimiters");

        st = new StringTokenizer("x;y", ";");
        check(st.hasMoreTokens(), "custom delimiter first token");
        check("x".equals(st.nextToken()), "custom delimiter x");
        check(st.hasMoreTokens(), "custom delimiter y remains");
        check("y".equals(st.nextToken()), "custom delimiter y");
        check(!st.hasMoreTokens(), "custom delimiter exhausted");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
