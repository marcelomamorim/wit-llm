/* @test    @summary Test StringCharacterIterator constructor with null text to verify NullPointerException    @run main StringCharacterIteratorWitupTest */

import java.text.StringCharacterIterator;

public class StringCharacterIteratorWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullText();
        testConstructorWithInvalidRange();
        testConstructorWithInvalidPosition();
        testConstructorValidParameters();
    }

    private static void testConstructorWithNullText() {
        try {
            new StringCharacterIterator(null, 0, 1, 0);
            System.err.println("Failed: Expected NullPointerException for null text");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected for null text");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
        }
    }

    private static void testConstructorWithInvalidRange() {
        String text = "abc";
        try {
            new StringCharacterIterator(text, -1, 3, 0);
            System.err.println("Failed: Expected IllegalArgumentException for begin < 0");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: IllegalArgumentException thrown as expected for begin < 0");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
        }
        try {
            new StringCharacterIterator(text, 0, 4, 0);
            System.err.println("Failed: Expected IllegalArgumentException for end > text.length()");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: IllegalArgumentException thrown as expected for end > text.length()");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
        }
        try {
            new StringCharacterIterator(text, 2, 1, 2);
            System.err.println("Failed: Expected IllegalArgumentException for begin > end");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: IllegalArgumentException thrown as expected for begin > end");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
        }
    }

    private static void testConstructorWithInvalidPosition() {
        String text = "abc";
        try {
            new StringCharacterIterator(text, 0, 3, -1);
            System.err.println("Failed: Expected IllegalArgumentException for position < begin");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: IllegalArgumentException thrown as expected for position < begin");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
        }
        try {
            new StringCharacterIterator(text, 0, 3, 4);
            System.err.println("Failed: Expected IllegalArgumentException for position > end");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: IllegalArgumentException thrown as expected for position > end");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
        }
    }

    private static void testConstructorValidParameters() {
        String text = "hello";
        try {
            StringCharacterIterator sci = new StringCharacterIterator(text, 1, 4, 2);
            if (sci != null) {
                System.out.println("Passed: Constructor with valid parameters succeeded");
            } else {
                System.err.println("Failed: Constructor returned null");
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }
}
