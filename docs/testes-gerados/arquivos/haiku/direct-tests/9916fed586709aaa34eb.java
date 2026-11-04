/*
 * @test
 * @summary Verifies observable behavior of Scanner.hasNext() for token presence, idempotence, delimiters and closed scanners
 * @run main ScannerWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class ScannerWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static Scanner scannerFor(String text) {
        InputStream in = new ByteArrayInputStream(text.getBytes());
        return new Scanner(in);
    }

    static void testHasNextWithTokens() {
        Scanner sc = scannerFor("1 2");
        check(sc.hasNext(), "hasNext should be true for '1 2' (first)");
        check("1".equals(sc.next()), "first token should be 1");
        check(sc.hasNext(), "hasNext should be true for remaining token 2");
        check("2".equals(sc.next()), "second token should be 2");
        check(!sc.hasNext(), "hasNext should be false after all tokens consumed");
        sc.close();
    }

    static void testHasNextEmptyInput() {
        Scanner sc = scannerFor("");
        check(!sc.hasNext(), "hasNext should be false for empty input");
        sc.close();
    }

    static void testHasNextWhitespaceOnly() {
        Scanner sc = scannerFor("   \n\t  ");
        check(!sc.hasNext(), "hasNext should be false for whitespace-only input");
        sc.close();
    }

    static void testHasNextIsIdempotent() {
        Scanner sc = scannerFor("abc");
        check(sc.hasNext(), "first hasNext should be true");
        check(sc.hasNext(), "second hasNext should still be true");
        check("abc".equals(sc.next()), "token should still be abc after repeated hasNext");
        sc.close();
    }

    static void testHasNextWithCustomDelimiter() {
        Scanner sc = scannerFor(",a,b");
        sc.useDelimiter(",");
        check(sc.hasNext(), "hasNext should be true with leading delimiter then token a");
        check("a".equals(sc.next()), "token should be a");
        check(sc.hasNext(), "hasNext should be true for token b");
        check("b".equals(sc.next()), "token should be b");
        check(!sc.hasNext(), "hasNext should be false at end");
        sc.close();
    }

    static void testHasNextOnClosedScanner() {
        Scanner sc = scannerFor("x");
        sc.close();
        boolean thrown = false;
        try {
            sc.hasNext();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "hasNext on closed scanner should throw IllegalStateException");
    }

    public static void main(String[] args) {
        testHasNextWithTokens();
        testHasNextEmptyInput();
        testHasNextWhitespaceOnly();
        testHasNextIsIdempotent();
        testHasNextWithCustomDelimiter();
        testHasNextOnClosedScanner();
    }
}
