/* @test    @summary Test the first() method of StringTokenIterator for correct behavior.    @run main StringTokenIteratorWitupTest */

import sun.util.locale.StringTokenIterator;

public class StringTokenIteratorWitupTest {

    public static void main(String[] args) {
        testFirstMethod();
    }

    public static void testFirstMethod() {
        String text = "a,b,c";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        // Move to second token to test reset
        iterator.next();
        // Call first() to reset to first token
        String firstToken = iterator.first();
        // The first token should be "a"
        if (!"a".equals(firstToken)) {
            throw new AssertionError("Expected first token to be 'a', but got: " + firstToken);
        }
        // After calling first(), current token should be 'a'
        String currentToken = iterator.current();
        if (!"a".equals(currentToken)) {
            throw new AssertionError("Expected current token to be 'a' after first(), but got: " + currentToken);
        }
        // The iterator should be at the start position
        int startPos = iterator.currentStart();
        if (startPos != 0) {
            throw new AssertionError("Expected start position to be 0 after first(), but got: " + startPos);
        }
        // The currentEnd should be at the position of first delimiter
        int endPos = iterator.currentEnd();
        if (endPos != 1) {
            throw new AssertionError("Expected end position to be 1 after first(), but got: " + endPos);
        }
        System.out.println("testFirstMethod passed.");
    }
}
