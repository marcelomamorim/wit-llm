/* @test    @summary Test StringBuilder.replace for valid and invalid ranges    @run main StringBuilderWitupTest */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testReplaceValidRange();
        testReplaceStartGreaterThanEnd();
        testReplaceNegativeStart();
        testReplaceEndExceedsLength();
        testReplaceStartEqualsEnd();
    }

    private static void testReplaceValidRange() {
        StringBuilder sb = new StringBuilder("Hello World");
        sb.replace(0, 5, "Hi");
        assert sb.toString().equals("Hi World") : "Failed: valid range replace";
    }

    private static void testReplaceStartGreaterThanEnd() {
        StringBuilder sb = new StringBuilder("Hello World");
        try {
            sb.replace(6, 5, "Test");
            assert false : "Expected StringIndexOutOfBoundsException for start > end";
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testReplaceNegativeStart() {
        StringBuilder sb = new StringBuilder("Hello World");
        try {
            sb.replace(-1, 5, "Test");
            assert false : "Expected StringIndexOutOfBoundsException for negative start";
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testReplaceEndExceedsLength() {
        StringBuilder sb = new StringBuilder("Hello World");
        try {
            sb.replace(0, 50, "Test");
            assert false : "Expected StringIndexOutOfBoundsException for end > length";
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testReplaceStartEqualsEnd() {
        StringBuilder sb = new StringBuilder("Hello World");
        sb.replace(5, 5, "");
        assert sb.toString().equals("Hello World") : "Failed: replace with start == end should not change string";
    }
}
