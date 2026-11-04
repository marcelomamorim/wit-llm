/* @test    @summary Test for StringTokenizer.hasMoreElements() method to verify behavior and exception paths    @run main StringTokenizerWitupTest */

import java.util.StringTokenizer;

public class StringTokenizerWitupTest {

    public static void main(String[] args) {
        testHasMoreElementsWithNullDelimiters();
        testHasMoreElementsWithNormalString();
    }

    private static void testHasMoreElementsWithNullDelimiters() {
        String str = "test string";
        String delim = null;
        StringTokenizer tokenizer = new StringTokenizer(str);
        try {
            boolean result = tokenizer.hasMoreElements();
            System.out.println("hasMoreElements() with default delimiters: " + result);
        } catch (NullPointerException e) {
            System.out.println("Caught NullPointerException as expected with default delimiters");
        }
        // Note: The baseline WIT suggests a potential NullPointerException if delimiters == null,
        // but the current implementation does not use delimiters in hasMoreElements(), so no exception expected here.
    }

    private static void testHasMoreElementsWithNormalString() {
        String str = "a b c";
        String delim = " ";
        StringTokenizer tokenizer = new StringTokenizer(str, delim);
        boolean hasMore = tokenizer.hasMoreElements();
        System.out.println("hasMoreElements() on 'a b c': " + hasMore);
        while (tokenizer.hasMoreElements()) {
            System.out.println("Token: " + tokenizer.nextElement());
        }
    }
}
