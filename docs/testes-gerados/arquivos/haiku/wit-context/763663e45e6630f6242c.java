/*
 * @test
 * @summary Witup-derived behavioral tests for Scanner.hasNext(Pattern)
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.Pattern;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testMatchingTokenReturnsTrueWithoutConsuming();
        testNonMatchingTokenReturnsFalse();
        testNullPatternThrowsNullPointerException();
        testClosedScannerThrowsIllegalStateException();
        testNullDelimiterThrowsIllegalArgumentException();
    }

    static void testMatchingTokenReturnsTrueWithoutConsuming() {
        Scanner sc = new Scanner("12 abc");
        Pattern digits = Pattern.compile("\\d+");
        check(sc.hasNext(digits), "hasNext should be true for first token '12'");
        check(sc.hasNext(digits), "hasNext must not consume the token");
        check("12".equals(sc.next()), "next should return '12' after hasNext");
        sc.close();
    }

    static void testNonMatchingTokenReturnsFalse() {
        Scanner sc = new Scanner("12 abc");
        Pattern letters = Pattern.compile("[a-z]+");
        check(!sc.hasNext(letters), "hasNext should be false when next token is '12'");
        check("12".equals(sc.next()), "token should remain available after false hasNext");
        sc.close();
    }

    static void testNullPatternThrowsNullPointerException() {
        final Scanner sc = new Scanner("12 abc");
        expectException(NullPointerException.class, () -> sc.hasNext((Pattern) null));
        sc.close();
    }

    static void testClosedScannerThrowsIllegalStateException() {
        final Scanner sc = new Scanner("12 abc");
        sc.close();
        final Pattern digits = Pattern.compile("\\d+");
        expectException(IllegalStateException.class, () -> sc.hasNext(digits));
    }

    static void testNullDelimiterThrowsIllegalArgumentException() {
        final Scanner sc = new Scanner("12 abc");
        sc.useDelimiter((Pattern) null);
        final Pattern digits = Pattern.compile("\\d+");
        expectException(IllegalArgumentException.class, () -> sc.hasNext(digits));
        sc.close();
    }

    interface Action {
        void run();
    }

    static void expectException(Class<? extends Throwable> type, Action action) {
        try {
            action.run();
        } catch (Throwable t) {
            if (type.isInstance(t)) {
                return;
            }
            throw new RuntimeException("unexpected exception: " + t, t);
        }
        throw new RuntimeException("expected " + type.getName());
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
