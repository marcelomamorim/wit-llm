/*
 * @test
 * @summary Test StringTokenizer.nextToken() for normal and boundary cases
 * @run main StringTokenizerWitupTest
 */

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class StringTokenizerWitupTest {
    public static void main(String[] args) {
        testNextTokenNormal();
        testNextTokenThrowsNoSuchElementException();
    }

    private static void testNextTokenNormal() {
        StringTokenizer st = new StringTokenizer("a,b,c", ",");
        String t1 = st.nextToken();
        if (!"a".equals(t1)) {
            throw new AssertionError("Expected token 'a' but got '" + t1 + "'");
        }
        String t2 = st.nextToken();
        if (!"b".equals(t2)) {
            throw new AssertionError("Expected token 'b' but got '" + t2 + "'");
        }
        String t3 = st.nextToken();
        if (!"c".equals(t3)) {
            throw new AssertionError("Expected token 'c' but got '" + t3 + "'");
        }
    }

    private static void testNextTokenThrowsNoSuchElementException() {
        StringTokenizer st = new StringTokenizer("");
        boolean thrown = false;
        try {
            st.nextToken();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when no tokens available");
        }
    }
}
