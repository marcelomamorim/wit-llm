/* @test    @summary Test String.subSequence method for valid and invalid indices    @run main StringWitupTest */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testSubSequenceValid();
        testSubSequenceBeginEqualsEnd();
        testSubSequenceBeginNegative();
        testSubSequenceEndExceedsLength();
        testSubSequenceBeginGreaterThanEnd();
    }

    static void testSubSequenceValid() {
        String s = "Hello, World!";
        CharSequence cs = s.subSequence(7, 12);
        if (!"World".contentEquals(cs)) {
            throw new AssertionError("Expected 'World', got '" + cs + "'");
        }
    }

    static void testSubSequenceBeginEqualsEnd() {
        String s = "Test";
        CharSequence cs = s.subSequence(2, 2);
        if (cs.length() != 0) {
            throw new AssertionError("Expected empty sequence, got length " + cs.length());
        }
    }

    static void testSubSequenceBeginNegative() {
        String s = "Negative";
        try {
            s.subSequence(-1, 4);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative beginIndex");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testSubSequenceEndExceedsLength() {
        String s = "Exceed";
        try {
            s.subSequence(2, 10);
            throw new AssertionError("Expected IndexOutOfBoundsException for endIndex > length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testSubSequenceBeginGreaterThanEnd() {
        String s = "Order";
        try {
            s.subSequence(4, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for beginIndex > endIndex");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
