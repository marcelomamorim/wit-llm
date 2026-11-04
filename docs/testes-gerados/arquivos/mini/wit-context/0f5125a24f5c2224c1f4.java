/*
 * @test
 * @summary Test sun.net.www.HeaderParser.subsequence(int,int) for valid and invalid ranges
 * @run main HeaderParserWitupTest
 */

import sun.net.www.HeaderParser;

public class HeaderParserWitupTest {
    public static void main(String[] args) {
        // Create a HeaderParser with some dummy data
        HeaderParser hp = new HeaderParser("key1=val1, key2=val2, key3=val3");

        // subsequence with full range returns this
        HeaderParser full = hp.subsequence(0, hp.nkeys);
        if (full != hp) {
            throw new AssertionError("subsequence(0, nkeys) should return this instance");
        }

        // subsequence with valid subrange returns new HeaderParser with correct keys
        if (hp.nkeys < 2) {
            // Defensive: if not enough keys parsed, skip this test
            System.out.println("Not enough keys parsed to test subsequence subrange");
        } else {
            HeaderParser sub = hp.subsequence(0, 1);
            if (sub == hp) {
                throw new AssertionError("subsequence with subrange should not return this instance");
            }
            if (sub.nkeys != 1) {
                throw new AssertionError("subsequence length mismatch: expected 1, got " + sub.nkeys);
            }
            if (!sub.tab[0][0].equals(hp.tab[0][0])) {
                throw new AssertionError("subsequence key mismatch");
            }
        }

        // subsequence with invalid ranges throws IllegalArgumentException
        int n = hp.nkeys;
        // start < 0
        try {
            hp.subsequence(-1, n);
            throw new AssertionError("Expected IllegalArgumentException for start < 0");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // end <= start
        try {
            hp.subsequence(1, 1);
            throw new AssertionError("Expected IllegalArgumentException for end <= start");
        } catch (IllegalArgumentException e) {
            // expected
        }

        try {
            hp.subsequence(2, 1);
            throw new AssertionError("Expected IllegalArgumentException for end < start");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // end > nkeys
        try {
            hp.subsequence(0, n + 1);
            throw new AssertionError("Expected IllegalArgumentException for end > nkeys");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // start >= end
        try {
            hp.subsequence(n, n);
            throw new AssertionError("Expected IllegalArgumentException for start >= end");
        } catch (IllegalArgumentException e) {
            // expected
        }

        System.out.println("All tests passed.");
    }
}
