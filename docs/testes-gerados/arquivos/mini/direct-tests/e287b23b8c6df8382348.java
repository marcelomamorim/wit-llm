/*
 * @test
 * @summary Basic tests for StringTokenizer.nextToken() method
 * @run main StringTokenizerWitupTest
 */

import java.util.StringTokenizer;
import java.util.NoSuchElementException;

public class StringTokenizerWitupTest {
    public static void main(String[] args) {
        testNextTokenBasic();
        testNextTokenWithReturnDelimsTrue();
        testNextTokenThrowsNoSuchElementException();
    }

    static void testNextTokenBasic() {
        StringTokenizer st = new StringTokenizer("a,b,c", ",");
        String t1 = st.nextToken();
        if (!"a".equals(t1)) {
            throw new Error("Expected token 'a' but got '" + t1 + "'");
        }
        String t2 = st.nextToken();
        if (!"b".equals(t2)) {
            throw new Error("Expected token 'b' but got '" + t2 + "'");
        }
        String t3 = st.nextToken();
        if (!"c".equals(t3)) {
            throw new Error("Expected token 'c' but got '" + t3 + "'");
        }
    }

    static void testNextTokenWithReturnDelimsTrue() {
        StringTokenizer st = new StringTokenizer("a,b", ",", true);
        String t1 = st.nextToken();
        if (!"a".equals(t1)) {
            throw new Error("Expected token 'a' but got '" + t1 + "'");
        }
        String t2 = st.nextToken();
        if (!",".equals(t2)) {
            throw new Error("Expected token ',' but got '" + t2 + "'");
        }
        String t3 = st.nextToken();
        if (!"b".equals(t3)) {
            throw new Error("Expected token 'b' but got '" + t3 + "'");
        }
    }

    static void testNextTokenThrowsNoSuchElementException() {
        StringTokenizer st = new StringTokenizer("");
        boolean thrown = false;
        try {
            st.nextToken();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new Error("Expected NoSuchElementException when no tokens available");
        }
    }
}
