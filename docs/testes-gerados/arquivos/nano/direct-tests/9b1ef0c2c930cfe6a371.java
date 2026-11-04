/* @test    @summary Test the setText method of StringTokenIterator for basic functionality and edge cases    @run main StringTokenIteratorWitupTest */

import sun.util.locale.StringTokenIterator;

public class StringTokenIteratorWitupTest {

    public static void main(String[] args) {
        testSetTextWithNormalString();
        testSetTextWithEmptyString();
        testSetTextWithLongString();
        testSetTextWithNullString();
    }

    static void testSetTextWithNormalString() {
        String text = "a,b,c";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        iterator.next(); // move to first token
        String tokenBefore = iterator.current();
        iterator.setText("x|y|z");
        String tokenAfter = iterator.current();
        if (!"x".equals(tokenAfter)) {
            throw new AssertionError("Expected token 'x' after setText, but got: " + tokenAfter);
        }
    }

    static void testSetTextWithEmptyString() {
        String text = "";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        iterator.setText("");
        if (iterator.current() != null) {
            throw new AssertionError("Expected null token for empty string, but got: " + iterator.current());
        }
        if (iterator.isDone() != true) {
            throw new AssertionError("Expected isDone() to be true for empty string");
        }
    }

    static void testSetTextWithLongString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append((char) ('a' + (i % 26)));
        }
        String longText = sb.toString();
        String dlms = "xyz";
        StringTokenIterator iterator = new StringTokenIterator("initial", ",");
        iterator.setText(longText);
        if (iterator.current() == null || iterator.current().length() == 0) {
            throw new AssertionError("Expected non-null, non-empty token after setText with long string");
        }
    }

    static void testSetTextWithNullString() {
        try {
            StringTokenIterator iterator = new StringTokenIterator("abc", ",");
            iterator.setText(null);
            throw new AssertionError("Expected NullPointerException when setting null text");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
