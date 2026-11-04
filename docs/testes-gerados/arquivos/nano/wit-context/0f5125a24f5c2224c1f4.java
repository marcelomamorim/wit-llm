/* @test    @summary Test for HeaderParser.subsequence(int, int) with valid and invalid inputs    @run main HeaderParserWitupTest */

import sun.net.www.HeaderParser;

public class HeaderParserWitupTest {

    public static void main(String[] args) {
        testValidSubsequence();
        testInvalidSubsequenceStartNegative();
        testInvalidSubsequenceStartGreaterThanEnd();
        testInvalidSubsequenceEndGreaterThanNkeys();
        testSubsequenceFullRange();
    }

    private static void testValidSubsequence() {
        String rawHeader = "timeout=15, max=5";
        HeaderParser parser = new HeaderParser(rawHeader);
        int start = 0;
        int end = parser.nkeys / 2;
        try {
            HeaderParser sub = parser.subsequence(start, end);
            if (sub == null || sub.nkeys != end - start) {
                throw new AssertionError("Valid subsequence returned null or incorrect size");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid subsequence", e);
        }
    }

    private static void testInvalidSubsequenceStartNegative() {
        String rawHeader = "timeout=15, max=5";
        HeaderParser parser = new HeaderParser(rawHeader);
        int start = -1;
        int end = 1;
        try {
            parser.subsequence(start, end);
            throw new AssertionError("Expected IllegalArgumentException for start = -1");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    private static void testInvalidSubsequenceStartGreaterThanEnd() {
        String rawHeader = "timeout=15, max=5";
        HeaderParser parser = new HeaderParser(rawHeader);
        int start = 2;
        int end = 1;
        try {
            parser.subsequence(start, end);
            throw new AssertionError("Expected IllegalArgumentException for start > end");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    private static void testInvalidSubsequenceEndGreaterThanNkeys() {
        String rawHeader = "timeout=15, max=5";
        HeaderParser parser = new HeaderParser(rawHeader);
        int start = 0;
        int end = parser.nkeys + 1;
        try {
            parser.subsequence(start, end);
            throw new AssertionError("Expected IllegalArgumentException for end > nkeys");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    private static void testSubsequenceFullRange() {
        String rawHeader = "timeout=15, max=5";
        HeaderParser parser = new HeaderParser(rawHeader);
        int start = 0;
        int end = parser.nkeys;
        try {
            HeaderParser sub = parser.subsequence(start, end);
            if (sub == null || sub.nkeys != end - start) {
                throw new AssertionError("Full range subsequence failed");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in full range subsequence", e);
        }
    }
}
