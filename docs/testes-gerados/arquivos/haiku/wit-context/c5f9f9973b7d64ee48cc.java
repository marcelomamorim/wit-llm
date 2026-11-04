/*
 * @test
 * @summary Verify Scanner.tokens() returns tokens, throws IllegalStateException when the scanner is closed, and closes the scanner when the returned stream is closed
 * @run main ScannerWitupTest
 */

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.Stream;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testTokensOpenScanner();
        testTokensOnClosedScannerThrows();
        testStreamCloseClosesScanner();
        testTokensOnEmptyInput();
    }

    static Scanner make(String s) {
        return new Scanner(new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8)));
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testTokensOpenScanner() {
        Scanner sc = make("alpha beta  gamma");
        Object[] arr = sc.tokens().toArray();
        check(arr.length == 3, "expected 3 tokens but got " + arr.length);
        check("alpha".equals(arr[0]), "token 0 mismatch: " + arr[0]);
        check("beta".equals(arr[1]), "token 1 mismatch: " + arr[1]);
        check("gamma".equals(arr[2]), "token 2 mismatch: " + arr[2]);
        sc.close();
    }

    static void testTokensOnClosedScannerThrows() {
        Scanner sc = make("a b");
        sc.close();
        boolean thrown = false;
        try {
            sc.tokens();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "tokens() on closed scanner must throw IllegalStateException");
    }

    static void testStreamCloseClosesScanner() {
        Scanner sc = make("a b");
        Stream<String> st = sc.tokens();
        st.close();
        boolean thrown = false;
        try {
            sc.hasNext();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "closing the token stream must close the scanner");
    }

    static void testTokensOnEmptyInput() {
        Scanner sc = make("   ");
        Object[] arr = sc.tokens().toArray();
        check(arr.length == 0, "expected no tokens but got " + arr.length);
        sc.close();
    }
}
