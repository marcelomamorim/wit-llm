/*
 * @test
 * @summary WITUP-derived behavioral tests for Scanner.skip(Pattern)
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

    static void testClosedScannerThrowsIllegalStateException() {
        Scanner sc = new Scanner("abc def");
        sc.close();
        try {
            sc.skip(Pattern.compile("abc"));
            throw new RuntimeException("Expected IllegalStateException on closed scanner");
        } catch (IllegalStateException expected) {
        }
    }

    static void testNullPatternThrowsNullPointerException() {
        Scanner sc = new Scanner("abc def");
        try {
            sc.skip((Pattern) null);
            throw new RuntimeException("Expected NullPointerException for null pattern");
        } catch (NullPointerException expected) {
        } finally {
            sc.close();
        }
    }

    static void testSkipMatchingPatternReturnsThisAndAdvances() {
        Scanner sc = new Scanner("abc def");
        Scanner result = sc.skip(Pattern.compile("abc "));
        check(result == sc, "skip must return the same scanner instance");
        check(sc.next().equals("def"), "next token after skip should be 'def'");
        sc.close();
    }

    static void testSkipNonMatchingPatternThrowsNoSuchElementException() {
        Scanner sc = new Scanner("abc");
        try {
            sc.skip(Pattern.compile("x"));
            throw new RuntimeException("Expected NoSuchElementException when pattern does not match");
        } catch (NoSuchElementException expected) {
        } finally {
            sc.close();
        }
    }

    public static void main(String[] args) {
        testClosedScannerThrowsIllegalStateException();
        testNullPatternThrowsNullPointerException();
        testSkipMatchingPatternReturnsThisAndAdvances();
        testSkipNonMatchingPatternThrowsNoSuchElementException();
    }
}
