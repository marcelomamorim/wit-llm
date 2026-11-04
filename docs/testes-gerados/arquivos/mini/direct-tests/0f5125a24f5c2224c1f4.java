/*
 * @test
 * @summary Test sun.net.www.HeaderParser subsequence method behavior
 * @run main HeaderParserWitupTest
 */

import sun.net.www.HeaderParser;

public class HeaderParserWitupTest {
    public static void main(String[] args) {
        testSubsequenceReturnsThisWhenFullRange();
        testSubsequenceReturnsCorrectSubsequence();
        testSubsequenceThrowsOnInvalidRange();
    }

    private static void testSubsequenceReturnsThisWhenFullRange() {
        HeaderParser hp = new HeaderParser("timeout=15, max=5");
        HeaderParser sub = hp.subsequence(0, hp.nkeys);
        if (sub != hp) {
            throw new RuntimeException("subsequence(0, nkeys) should return this instance");
        }
    }

    private static void testSubsequenceReturnsCorrectSubsequence() {
        HeaderParser hp = new HeaderParser("timeout=15, max=5, basic, realm=FuzzFace");
        int originalKeys = hp.nkeys;
        if (originalKeys < 3) {
            throw new RuntimeException("Test setup failure: expected at least 3 keys parsed");
        }
        // Extract subsequence from 1 to 3 (should include keys at index 1 and 2)
        HeaderParser sub = hp.subsequence(1, 3);
        if (sub == hp) {
            throw new RuntimeException("subsequence with partial range should not return this instance");
        }
        if (sub.nkeys != 2) {
            throw new RuntimeException("subsequence nkeys expected 2 but was " + sub.nkeys);
        }
        // Check that keys in subsequence match original keys at those positions
        for (int i = 0; i < sub.nkeys; i++) {
            String[] origPair = hp.tab[i + 1];
            String[] subPair = sub.tab[i];
            if (origPair == null || subPair == null) {
                throw new RuntimeException("Null key/value pair encountered");
            }
            if (!equals(origPair[0], subPair[0]) || !equals(origPair[1], subPair[1])) {
                throw new RuntimeException("Key/value mismatch at index " + i + ": expected [" + origPair[0] + "," + origPair[1] + "] but got [" + subPair[0] + "," + subPair[1] + "]");
            }
        }
    }

    private static void testSubsequenceThrowsOnInvalidRange() {
        HeaderParser hp = new HeaderParser("timeout=15, max=5");
        int n = hp.nkeys;
        boolean thrown;

        // start < 0
        thrown = false;
        try {
            hp.subsequence(-1, n);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for start < 0");
        }

        // start >= end
        thrown = false;
        try {
            hp.subsequence(1, 1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for start >= end");
        }

        // end > nkeys
        thrown = false;
        try {
            hp.subsequence(0, n + 1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for end > nkeys");
        }
    }

    private static boolean equals(String a, String b) {
        if (a == null) {
            return b == null;
        }
        return a.equals(b);
    }
}
