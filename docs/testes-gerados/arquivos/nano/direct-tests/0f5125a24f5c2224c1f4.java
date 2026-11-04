/* @test    @summary Test HeaderParser.subsequence method for valid and invalid ranges    @run main HeaderParserWitupTest */

import sun.net.www.HeaderParser;

public class HeaderParserWitupTest {

    public static void main(String[] args) {
        testValidSubsequence();
        testFullRangeReturnsSameInstance();
        testInvalidStartNegative();
        testInvalidEndExceedsNkeys();
        testInvalidStartGreaterThanEnd();
    }

    static void testValidSubsequence() {
        String rawHeader = "timeout=15, max=5";
        HeaderParser parser = new HeaderParser(rawHeader);
        int nkeys = parser.nkeys;
        // Valid range: from 0 to nkeys
        HeaderParser sub = parser.subsequence(0, nkeys);
        assert sub != null : "Subsequence should not be null";
        assert sub != parser : "Subsequence should be a new instance";
        assert sub.nkeys == nkeys : "Subsequence nkeys should match original";
    }

    static void testFullRangeReturnsSameInstance() {
        String rawHeader = "key1=value1";
        HeaderParser parser = new HeaderParser(rawHeader);
        HeaderParser sub = parser.subsequence(0, parser.nkeys);
        assert sub == parser : "Full range subsequence should return the same instance";
    }

    static void testInvalidStartNegative() {
        String rawHeader = "a=b";
        HeaderParser parser = new HeaderParser(rawHeader);
        try {
            parser.subsequence(-1, 1);
            assert false : "Expected IllegalArgumentException for negative start";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidEndExceedsNkeys() {
        String rawHeader = "a=b";
        HeaderParser parser = new HeaderParser(rawHeader);
        try {
            parser.subsequence(0, parser.nkeys + 1);
            assert false : "Expected IllegalArgumentException for end > nkeys";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidStartGreaterThanEnd() {
        String rawHeader = "a=b";
        HeaderParser parser = new HeaderParser(rawHeader);
        try {
            parser.subsequence(1, 0);
            assert false : "Expected IllegalArgumentException for start > end";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
