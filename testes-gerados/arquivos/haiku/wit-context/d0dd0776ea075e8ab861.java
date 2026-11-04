/*
 * @test
 * @summary Witup-derived tests for Scanner.findWithinHorizon
 * @run main ScannerWitupTest
 */

import java.io.StringReader;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testSuccessfulFind();
        testNoMatchReturnsNull();
        testHorizonLimitsSearch();
        testClosedScannerThrowsIllegalState();
        testNullPatternThrowsNPE();
        testNegativeHorizonThrowsIAE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSuccessfulFind() {
        Scanner sc = new Scanner(new StringReader("abc 123 def"));
        String result = sc.findWithinHorizon(Pattern.compile("\\d+"), 0);
        check("123".equals(result), "expected 123 but was " + result);
        sc.close();
    }

    static void testNoMatchReturnsNull() {
        Scanner sc = new Scanner(new StringReader("abc def"));
        String result = sc.findWithinHorizon(Pattern.compile("xyz"), 0);
        check(result == null, "expected null but was " + result);
        sc.close();
    }

    static void testHorizonLimitsSearch() {
        Scanner sc = new Scanner(new StringReader("abcdef123"));
        String result = sc.findWithinHorizon(Pattern.compile("abc"), 3);
        check("abc".equals(result), "expected abc but was " + result);
        sc.close();
    }

    static void testClosedScannerThrowsIllegalState() {
        Scanner sc = new Scanner(new StringReader("abc"));
        sc.close();
        try {
            sc.findWithinHorizon(Pattern.compile("a"), 0);
            throw new RuntimeException("IllegalStateException expected");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void testNullPatternThrowsNPE() {
        Scanner sc = new Scanner(new StringReader("abc"));
        try {
            sc.findWithinHorizon(null, 0);
            throw new RuntimeException("NullPointerException expected");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            sc.close();
        }
    }

    static void testNegativeHorizonThrowsIAE() {
        Scanner sc = new Scanner(new StringReader("abc"));
        try {
            sc.findWithinHorizon(Pattern.compile("a"), -1);
            throw new RuntimeException("IllegalArgumentException expected");
        } catch (IllegalArgumentException expected) {
            // expected
        } finally {
            sc.close();
        }
    }
}
