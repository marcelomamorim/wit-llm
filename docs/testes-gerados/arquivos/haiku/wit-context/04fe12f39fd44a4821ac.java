/*
 * @test
 * @summary Witup-derived checks for Scanner.findInLine(Pattern)
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.Pattern;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testClosedThrowsIllegalStateException();
        testNullPatternThrowsNullPointerException();
        testReturnsMatchOnCurrentLine();
        testReturnsNullWhenNothingBeforeLineEnd();
        testReturnsNullWhenNoMatch();
        testDoesNotSearchBeyondCurrentLine();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testClosedThrowsIllegalStateException() {
        Scanner sc = new Scanner("abc 123");
        sc.close();
        try {
            sc.findInLine(Pattern.compile("\\d+"));
            throw new RuntimeException("expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void testNullPatternThrowsNullPointerException() {
        Scanner sc = new Scanner("abc 123");
        try {
            sc.findInLine((Pattern) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            sc.close();
        }
    }

    static void testReturnsMatchOnCurrentLine() {
        Scanner sc = new Scanner("abc 123 def\nxyz");
        String result = sc.findInLine(Pattern.compile("\\d+"));
        check("123".equals(result), "expected 123 but was " + result);
        sc.close();
    }

    static void testReturnsNullWhenNothingBeforeLineEnd() {
        Scanner sc = new Scanner("\nabc");
        String result = sc.findInLine(Pattern.compile("\\w+"));
        check(result == null, "expected null but was " + result);
        sc.close();
    }

    static void testReturnsNullWhenNoMatch() {
        Scanner sc = new Scanner("abc def");
        String result = sc.findInLine(Pattern.compile("\\d+"));
        check(result == null, "expected null but was " + result);
        sc.close();
    }

    static void testDoesNotSearchBeyondCurrentLine() {
        Scanner sc = new Scanner("abc\n123");
        String result = sc.findInLine(Pattern.compile("\\d+"));
        check(result == null, "expected null but was " + result);
        sc.close();
    }
}
