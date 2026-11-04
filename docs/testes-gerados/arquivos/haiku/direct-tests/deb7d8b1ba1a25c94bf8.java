/*
 * @test
 * @summary Behavioral tests for Scanner.next() on string input: token return, whitespace skipping, and NoSuchElementException at end of input.
 * @run main ScannerWitupTest
 */

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ScannerWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSequentialTokens() {
        Scanner sc = new Scanner("alpha beta");
        check("alpha".equals(sc.next()), "first next() should return alpha");
        check("beta".equals(sc.next()), "second next() should return beta");
        sc.close();
    }

    static void testLeadingAndTrailingWhitespaceSkipped() {
        Scanner sc = new Scanner("   token   ");
        check("token".equals(sc.next()), "next() should skip surrounding whitespace");
        sc.close();
    }

    static void testNewlineIsDelimiter() {
        Scanner sc = new Scanner("one\ntwo\r\nthree");
        check("one".equals(sc.next()), "token before newline");
        check("two".equals(sc.next()), "token before CRLF");
        check("three".equals(sc.next()), "last token");
        sc.close();
    }

    static void testCustomDelimiter() {
        Scanner sc = new Scanner("a,b,,c").useDelimiter(",");
        check("a".equals(sc.next()), "custom delimiter first token");
        check("b".equals(sc.next()), "custom delimiter second token");
        check("c".equals(sc.next()), "custom delimiter token after empty field");
        sc.close();
    }

    static void testNextOnEmptyInputThrows() {
        Scanner sc = new Scanner("");
        boolean thrown = false;
        try {
            sc.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() on empty input must throw NoSuchElementException");
        sc.close();
    }

    static void testNextAfterExhaustionThrows() {
        Scanner sc = new Scanner("only");
        check("only".equals(sc.next()), "first token");
        boolean thrown = false;
        try {
            sc.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() after last token must throw NoSuchElementException");
        sc.close();
    }

    public static void main(String[] args) {
        testSequentialTokens();
        testLeadingAndTrailingWhitespaceSkipped();
        testNewlineIsDelimiter();
        testCustomDelimiter();
        testNextOnEmptyInputThrows();
        testNextAfterExhaustionThrows();
    }
}
