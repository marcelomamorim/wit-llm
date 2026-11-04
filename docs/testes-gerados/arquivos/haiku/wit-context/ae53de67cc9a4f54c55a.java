/*
 * @test
 * @summary Witup generated tests for Scanner.skip(String pattern)
 * @run main ScannerWitupTest
 */

import java.io.StringReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ScannerWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSkipMatchingPatternConsumesInput() {
        Scanner sc = new Scanner(new StringReader("  fish 12"));
        try {
            Scanner returned = sc.skip("\\s*fish\\s*");
            check(returned == sc, "skip(String) should return this scanner");
            check(sc.nextInt() == 12, "token after skipped pattern should be 12");
        } finally {
            sc.close();
        }
    }

    static void testSkipNonMatchingPatternThrowsNoSuchElement() {
        Scanner sc = new Scanner(new StringReader("12"));
        try {
            sc.skip("fish");
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            check(sc.nextInt() == 12, "input must not be consumed by failed skip");
        } finally {
            sc.close();
        }
    }

    static void testSkipOnClosedScannerThrowsIllegalState() {
        Scanner sc = new Scanner(new StringReader("fish 1"));
        sc.close();
        try {
            sc.skip("fish");
            throw new RuntimeException("expected IllegalStateException on closed scanner");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        testSkipMatchingPatternConsumesInput();
        testSkipNonMatchingPatternThrowsNoSuchElement();
        testSkipOnClosedScannerThrowsIllegalState();
    }
}
