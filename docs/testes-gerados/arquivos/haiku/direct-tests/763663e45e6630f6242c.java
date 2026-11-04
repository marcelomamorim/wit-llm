/*
 * @test
 * @summary Tests observable behavior of Scanner.hasNext(Pattern)
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.Pattern;

public class ScannerWitupTest {

    public static void main(String[] args) throws Exception {
        testMatchingTokenNotConsumed();
        testNonMatchingTokenNotConsumed();
        testRepeatedCallsAreIdempotent();
        testEmptyInput();
        testWholeTokenMustMatch();
        testNullPattern();
        testClosedScanner();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testMatchingTokenNotConsumed() {
        Scanner sc = new Scanner("abc 123");
        check(sc.hasNext(Pattern.compile("abc")), "hasNext should be true for matching token");
        check("abc".equals(sc.next()), "token must not be consumed by hasNext(Pattern)");
        sc.close();
    }

    static void testNonMatchingTokenNotConsumed() {
        Scanner sc = new Scanner("abc 123");
        check(!sc.hasNext(Pattern.compile("xyz")), "hasNext should be false for non-matching token");
        check("abc".equals(sc.next()), "state must be reverted after false result");
        sc.close();
    }

    static void testRepeatedCallsAreIdempotent() {
        Scanner sc = new Scanner("abc");
        Pattern p = Pattern.compile("a.c");
        check(sc.hasNext(p), "first call should be true");
        check(sc.hasNext(p), "second call should be true");
        check("abc".equals(sc.next()), "token should still be available");
        sc.close();
    }

    static void testEmptyInput() {
        Scanner sc = new Scanner("");
        check(!sc.hasNext(Pattern.compile("a")), "empty input should yield false");
        sc.close();
    }

    static void testWholeTokenMustMatch() {
        Scanner sc = new Scanner("abcd");
        check(!sc.hasNext(Pattern.compile("abc")), "partial token match should yield false");
        check("abcd".equals(sc.next()), "token should remain unconsumed");
        sc.close();
    }

    static void testNullPattern() {
        Scanner sc = new Scanner("abc");
        try {
            sc.hasNext((Pattern) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        sc.close();
    }

    static void testClosedScanner() {
        Scanner sc = new Scanner("abc");
        sc.close();
        try {
            sc.hasNext(Pattern.compile("abc"));
            throw new RuntimeException("expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
