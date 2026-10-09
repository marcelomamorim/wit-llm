/* @test    @summary Test for StringTokenizer.nextToken(String delim) method handling null delimiter and end of tokens    @run main StringTokenizerWitupTest_P3b9d2bc4db74 */

import java.util.StringTokenizer;

public class StringTokenizerWitupTest_P3b9d2bc4db74 {

    public static void main(String[] args) {
        testNextTokenWithNullDelimiterAtEnd();
        testNextTokenWithValidDelimiter();
        testNextTokenWithNullDelimiterMid();
    }

    private static void testNextTokenWithNullDelimiterAtEnd() {
        String str = "abc def";
        StringTokenizer st = new StringTokenizer(str);
        try {
            // Consume tokens
            while (st.hasMoreTokens()) {
                st.nextToken();
            }
            // Now, call with null delimiter at end, expecting NoSuchElementException
            st.nextToken(null);
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testNextTokenWithValidDelimiter() {
        String str = "abc,def,ghi";
        StringTokenizer st = new StringTokenizer(str, ",");
        try {
            String token1 = st.nextToken(",");
            if (!"abc".equals(token1)) {
                throw new AssertionError("Expected 'abc', got: " + token1);
            }
            String token2 = st.nextToken(",");
            if (!"def".equals(token2)) {
                throw new AssertionError("Expected 'def', got: " + token2);
            }
            String token3 = st.nextToken(",");
            if (!"ghi".equals(token3)) {
                throw new AssertionError("Expected 'ghi', got: " + token3);
            }
            if (st.hasMoreTokens()) {
                throw new AssertionError("Expected no more tokens");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testNextTokenWithNullDelimiterMid() {
        String str = "abc def";
        StringTokenizer st = new StringTokenizer(str);
        try {
            st.nextToken(); // 'abc'
            st.nextToken(); // 'def'
            // Now, call with null delimiter, expecting NoSuchElementException
            st.nextToken(null);
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
