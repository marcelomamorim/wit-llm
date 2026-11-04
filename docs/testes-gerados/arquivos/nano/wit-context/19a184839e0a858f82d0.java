/* @test    @summary Test for StringTokenIterator.first() method to verify behavior and exception paths    @run main StringTokenIteratorWitupTest */

import sun.util.locale.StringTokenIterator;

public class StringTokenIteratorWitupTest {

    public static void main(String[] args) {
        testNormalCase();
        testEmptyText();
        testNullDlms();
        testNegativeStart();
        testStartBeyondLength();
        testStartAtDelimiter();
        testStartAtEnd();
    }

    static void testNormalCase() {
        String text = "a,b,c";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        String result = iterator.first();
        if (result == null || !result.equals("a")) {
            throw new AssertionError("Expected first token 'a', got: " + result);
        }
    }

    static void testEmptyText() {
        String text = "";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        String result = iterator.first();
        if (result != null) {
            throw new AssertionError("Expected null for empty text, got: " + result);
        }
    }

    static void testNullDlms() {
        String text = "abc";
        String dlms = null;
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        String result = iterator.first();
        if (result == null || !result.equals("a")) {
            throw new AssertionError("Expected first token 'a' with null dlms, got: " + result);
        }
    }

    static void testNegativeStart() {
        String text = "abc";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        try {
            iterator.setStart(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative start");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testStartBeyondLength() {
        String text = "abc";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        try {
            iterator.setStart(10);
            throw new AssertionError("Expected IndexOutOfBoundsException for start beyond length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testStartAtDelimiter() {
        String text = "a,b,c";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        iterator.setStart(1); // position at comma
        String token = iterator.first();
        if (token == null || !token.equals("b")) {
            throw new AssertionError("Expected token 'b', got: " + token);
        }
    }

    static void testStartAtEnd() {
        String text = "abc";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        iterator.setStart(text.length()); // at end
        String token = iterator.first();
        if (token != null) {
            throw new AssertionError("Expected null at end, got: " + token);
        }
    }
}
