/*
 * @test
 * @summary Scanner.skip(Pattern) matches at the current position, advances the scanner, returns this, and rejects null or non-matching input
 * @run main ScannerWitupTest
 */

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ScannerWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        testSkipAdvancesAndReturnsThis();
        testSkipWhitespaceThenNext();
        testNullPatternThrowsNPE();
        testNonMatchingPatternThrowsNoSuchElement();
        testMatchMustBeAtCurrentPosition();
    }

    static void testSkipAdvancesAndReturnsThis() {
        Scanner s = new Scanner("abc123");
        s.hasNext();
        Scanner r = s.skip(Pattern.compile("abc"));
        check(r == s, "skip must return this scanner");
        check(s.nextInt() == 123, "skip must advance past the matched text");
    }

    static void testSkipWhitespaceThenNext() {
        Scanner s = new Scanner("  x y");
        s.hasNext();
        s.skip(Pattern.compile("\\s+"));
        check(s.next().equals("x"), "next token after skipping whitespace must be x");
    }

    static void testNullPatternThrowsNPE() {
        Scanner s = new Scanner("abc");
        boolean thrown = false;
        try {
            s.skip((Pattern) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "skip(null) must throw NullPointerException");
    }

    static void testNonMatchingPatternThrowsNoSuchElement() {
        Scanner s = new Scanner("abc");
        boolean thrown = false;
        try {
            s.skip(Pattern.compile("\\d"));
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "skip with non-matching pattern must throw NoSuchElementException");
    }

    static void testMatchMustBeAtCurrentPosition() {
        Scanner s = new Scanner("ab12");
        boolean thrown = false;
        try {
            s.skip(Pattern.compile("\\d"));
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "skip must anchor the match at the current position");
    }
}
