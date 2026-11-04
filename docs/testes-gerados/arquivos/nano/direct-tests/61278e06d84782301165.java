/* @test    @summary Test StringBuilder.append(CharSequence, int, int) for correctness and regressions    @run main StringBuilderWitupTest */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testAppendWithValidIndices();
        testAppendWithStartEqualsEnd();
        testAppendWithStartGreaterThanEnd();
        testAppendWithNullCharSequence();
    }

    private static void testAppendWithValidIndices() {
        StringBuilder sb = new StringBuilder("HelloWorld");
        CharSequence seq = "JavaProgramming";
        StringBuilder result = sb.append(seq, 0, 4); // should append "Java"
        String expected = "HelloWorldJava";
        if (!result.toString().equals(expected)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result.toString());
        }
    }

    private static void testAppendWithStartEqualsEnd() {
        StringBuilder sb = new StringBuilder("Test");
        CharSequence seq = "Sample";
        StringBuilder result = sb.append(seq, 2, 2); // should append "" (nothing)
        String expected = "Test";
        if (!result.toString().equals(expected)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result.toString());
        }
    }

    private static void testAppendWithStartGreaterThanEnd() {
        StringBuilder sb = new StringBuilder("Data");
        CharSequence seq = "MoreData";
        try {
            sb.append(seq, 5, 3); // invalid indices, should throw StringIndexOutOfBoundsException
            throw new AssertionError("Expected StringIndexOutOfBoundsException was not thrown");
        } catch (IndexOutOfBoundsException e) {
            // expected exception
        }
    }

    private static void testAppendWithNullCharSequence() {
        StringBuilder sb = new StringBuilder("NullTest");
        CharSequence seq = null;
        try {
            sb.append(seq, 0, 0); // should throw NullPointerException
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // expected exception
        }
    }
}
