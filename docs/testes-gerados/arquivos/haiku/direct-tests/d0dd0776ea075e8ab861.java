/*
 * @test
 * @summary Behavioral tests for Scanner.findWithinHorizon(Pattern, int)
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.Pattern;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testFindsMatchWithUnlimitedHorizon();
        testReturnsNullWhenNoMatch();
        testHorizonLimitsSearchWindow();
        testMatchAdvancesScannerPosition();
        testNullPatternThrowsNPE();
        testNegativeHorizonThrowsIAE();
        System.out.println("All tests passed");
    }

    static void testFindsMatchWithUnlimitedHorizon() {
        Scanner sc = new Scanner("abc 123 def");
        String r = sc.findWithinHorizon(Pattern.compile("\\d+"), 0);
        check("123".equals(r), "expected 123 but was " + r);
        sc.close();
    }

    static void testReturnsNullWhenNoMatch() {
        Scanner sc = new Scanner("abc def");
        String r = sc.findWithinHorizon(Pattern.compile("\\d+"), 0);
        check(r == null, "expected null but was " + r);
        sc.close();
    }

    static void testHorizonLimitsSearchWindow() {
        Scanner sc = new Scanner("abcdef123");
        String r = sc.findWithinHorizon(Pattern.compile("\\d+"), 3);
        check(r == null, "expected null within horizon 3 but was " + r);
        sc.close();
    }

    static void testMatchAdvancesScannerPosition() {
        Scanner sc = new Scanner("abc 123 def");
        String r = sc.findWithinHorizon(Pattern.compile("\\d+"), 0);
        check("123".equals(r), "expected 123 but was " + r);
        String next = sc.next();
        check("def".equals(next), "expected def after match but was " + next);
        sc.close();
    }

    static void testNullPatternThrowsNPE() {
        Scanner sc = new Scanner("abc");
        boolean thrown = false;
        try {
            sc.findWithinHorizon((Pattern) null, 0);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null pattern");
        sc.close();
    }

    static void testNegativeHorizonThrowsIAE() {
        Scanner sc = new Scanner("abc");
        boolean thrown = false;
        try {
            sc.findWithinHorizon(Pattern.compile("a"), -1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException for negative horizon");
        sc.close();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
