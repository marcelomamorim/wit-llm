/*
 * @test
 * @summary Verifies Scanner.next(String pattern) returns matching tokens, skips delimiters, and throws for mismatches or missing input
 * @run main ScannerWitupTest
 */

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ScannerWitupTest {
    public static void main(String[] args) {
        Scanner s = new Scanner("abc 123");
        check("abc".equals(s.next("[a-z]+")), "first token should be abc");
        check("123".equals(s.next("\\d+")), "second token should be 123");

        Scanner s2 = new Scanner("   42 x");
        check("42".equals(s2.next("\\d+")), "leading whitespace should be skipped");

        Scanner s3 = new Scanner("a1 b2");
        check("a1".equals(s3.next("[a-z]\\d")), "first pattern token should be a1");
        check("b2".equals(s3.next("[a-z]\\d")), "second pattern token should be b2");

        Scanner s4 = new Scanner("abc");
        boolean mismatch = false;
        try {
            s4.next("ab");
        } catch (InputMismatchException e) {
            mismatch = true;
        }
        check(mismatch, "partial match of token should throw InputMismatchException");

        Scanner s5 = new Scanner("   ");
        boolean empty = false;
        try {
            s5.next("x");
        } catch (NoSuchElementException e) {
            empty = true;
        }
        check(empty, "input with only delimiters should throw NoSuchElementException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
