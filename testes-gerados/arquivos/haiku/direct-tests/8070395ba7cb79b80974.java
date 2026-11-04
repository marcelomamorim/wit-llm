/*
 * @test
 * @summary Verifies public observable behavior of Scanner.findWithinHorizon(String, int)
 * @run main ScannerWitupTest
 */

import java.util.Scanner;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testFindsMatchAndReturnsMatchedText();
        testAdvancesPositionPastMatch();
        testReturnsNullWhenNoMatch();
        testHorizonLimitsSearchWindow();
        testSkipsDelimitersBeforeMatch();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFindsMatchAndReturnsMatchedText() {
        Scanner sc = new Scanner("abc 123 def");
        String found = sc.findWithinHorizon("\\d+", 0);
        check("123".equals(found), "expected '123' but was " + found);
        sc.close();
    }

    static void testAdvancesPositionPastMatch() {
        Scanner sc = new Scanner("abc 123 def");
        sc.findWithinHorizon("\\d+", 0);
        String next = sc.next();
        check("def".equals(next), "expected next token 'def' but was " + next);
        sc.close();
    }

    static void testReturnsNullWhenNoMatch() {
        Scanner sc = new Scanner("abc");
        String found = sc.findWithinHorizon("\\d+", 0);
        check(found == null, "expected null but was " + found);
        sc.close();
    }

    static void testHorizonLimitsSearchWindow() {
        Scanner narrow = new Scanner("abc123");
        String none = narrow.findWithinHorizon("\\d+", 2);
        check(none == null, "expected null with horizon 2 but was " + none);
        narrow.close();

        Scanner wide = new Scanner("abc123");
        String found = wide.findWithinHorizon("\\d+", 10);
        check("123".equals(found), "expected '123' with horizon 10 but was " + found);
        wide.close();
    }

    static void testSkipsDelimitersBeforeMatch() {
        Scanner sc = new Scanner("  foo  bar");
        String found = sc.findWithinHorizon("bar", 0);
        check("bar".equals(found), "expected 'bar' but was " + found);
        sc.close();
    }
}
