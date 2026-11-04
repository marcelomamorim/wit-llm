/*
 * @test
 * @summary Witup-derived checks for Scanner.next(Pattern): closed state, null pattern, matching, mismatch and end of input
 * @run main ScannerWitupTest
 */

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testClosedScannerThrowsIllegalStateException();
        testClosedCheckPrecedesNullCheck();
        testNullPatternThrowsNullPointerException();
        testMatchingTokenIsReturned();
        testNonMatchingTokenThrowsInputMismatchException();
        testEmptyInputThrowsNoSuchElementException();
        testHasNextThenNextReturnsSameToken();
    }

    static void testClosedScannerThrowsIllegalStateException() {
        Scanner sc = new Scanner("123 456");
        sc.close();
        expectFailure(IllegalStateException.class,
                () -> sc.next(Pattern.compile("\\d+")));
    }

    static void testClosedCheckPrecedesNullCheck() {
        Scanner sc = new Scanner("123");
        sc.close();
        expectFailure(IllegalStateException.class,
                () -> sc.next((Pattern) null));
    }

    static void testNullPatternThrowsNullPointerException() {
        Scanner sc = new Scanner("123");
        try {
            expectFailure(NullPointerException.class,
                    () -> sc.next((Pattern) null));
        } finally {
            sc.close();
        }
    }

    static void testMatchingTokenIsReturned() {
        Scanner sc = new Scanner("1 fish 2");
        try {
            String tok = sc.next(Pattern.compile("\\d+"));
            check("1".equals(tok), "expected 1 but got " + tok);
        } finally {
            sc.close();
        }
    }

    static void testNonMatchingTokenThrowsInputMismatchException() {
        Scanner sc = new Scanner("abc");
        try {
            expectFailure(InputMismatchException.class,
                    () -> sc.next(Pattern.compile("\\d+")));
        } finally {
            sc.close();
        }
    }

    static void testEmptyInputThrowsNoSuchElementException() {
        Scanner sc = new Scanner("");
        try {
            expectFailure(NoSuchElementException.class,
                    () -> sc.next(Pattern.compile("\\d+")));
        } finally {
            sc.close();
        }
    }

    static void testHasNextThenNextReturnsSameToken() {
        Scanner sc = new Scanner("42 7");
        try {
            Pattern p = Pattern.compile("\\d+");
            check(sc.hasNext(p), "hasNext(pattern) should be true");
            String tok = sc.next(p);
            check("42".equals(tok), "expected 42 but got " + tok);
        } finally {
            sc.close();
        }
    }

    static void expectFailure(Class<? extends Throwable> type, Runnable r) {
        try {
            r.run();
        } catch (Throwable t) {
            if (type.isInstance(t)) {
                return;
            }
            throw new AssertionError("expected " + type.getName() + " but got " + t, t);
        }
        throw new AssertionError("expected " + type.getName() + " but no exception was thrown");
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new AssertionError(msg);
        }
    }
}
