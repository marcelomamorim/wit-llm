/*
 * @test
 * @summary Tests for StringBuilder.append(CharSequence s, int start, int end)
 * @run main StringBuilderWitupTest
 */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testAppendWithValidRange();
        testAppendWithEndGreaterThanLength();
        testAppendWithNullCharSequence();
        testAppendWithNullCharSequenceAndInvalidRange();
    }

    private static void testAppendWithValidRange() {
        StringBuilder sb = new StringBuilder("Hello");
        CharSequence seq = "World";
        try {
            sb.append(seq, 0, 5);
            if (!sb.toString().equals("HelloWorld")) {
                throw new AssertionError("Failed: Expected 'HelloWorld', got '" + sb.toString() + "'");
            }
        } catch (IndexOutOfBoundsException e) {
            throw new AssertionError("Unexpected exception for valid range", e);
        }
    }

    private static void testAppendWithEndGreaterThanLength() {
        StringBuilder sb = new StringBuilder("Test");
        CharSequence seq = "Example";
        try {
            sb.append(seq, 0, 10);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testAppendWithNullCharSequence() {
        StringBuilder sb = new StringBuilder("NullTest");
        try {
            sb.append((CharSequence) null, 0, 0);
            if (!sb.toString().equals("NullTest")) {
                throw new AssertionError("Failed: StringBuilder should remain unchanged when appending null CharSequence");
            }
        } catch (NullPointerException e) {
            throw new AssertionError("Unexpected NullPointerException when appending null CharSequence", e);
        }
    }

    private static void testAppendWithNullCharSequenceAndInvalidRange() {
        StringBuilder sb = new StringBuilder("NullRange");
        try {
            sb.append((CharSequence) null, -1, 2);
            throw new AssertionError("Expected NullPointerException or IndexOutOfBoundsException not thrown");
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
