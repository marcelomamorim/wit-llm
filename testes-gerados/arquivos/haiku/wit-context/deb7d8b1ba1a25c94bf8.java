/*
 * @test
 * @summary Witup-derived tests for Scanner.next()
 * @run main ScannerWitupTest
 */

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ScannerWitupTest {
    public static void main(String[] args) {
        testNextAfterCloseThrowsISE();
        testSequentialTokens();
        testCustomDelimiter();
        testEmptyInputThrowsNSEE();
        testNextOnExhaustedThrowsNSEE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNextAfterCloseThrowsISE() {
        Scanner s = new Scanner("abc");
        s.close();
        try {
            s.next();
            throw new RuntimeException("expected IllegalStateException");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    static void testSequentialTokens() {
        Scanner s = new Scanner("alpha beta");
        check("alpha".equals(s.next()), "first token");
        check("beta".equals(s.next()), "second token");
        s.close();
    }

    static void testCustomDelimiter() {
        Scanner s = new Scanner("1,fish,2").useDelimiter(",");
        check("1".equals(s.next()), "token 1");
        check("fish".equals(s.next()), "token 2");
        check("2".equals(s.next()), "token 3");
        s.close();
    }

    static void testEmptyInputThrowsNSEE() {
        Scanner s = new Scanner("   ");
        try {
            s.next();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            // expected
        } finally {
            s.close();
        }
    }

    static void testNextOnExhaustedThrowsNSEE() {
        Scanner s = new Scanner("a");
        check("a".equals(s.next()), "single token");
        try {
            s.next();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            // expected
        } finally {
            s.close();
        }
    }
}
