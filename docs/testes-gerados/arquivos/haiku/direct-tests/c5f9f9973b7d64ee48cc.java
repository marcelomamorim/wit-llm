/*
 * @test
 * @summary Scanner.tokens() yields delimiter-separated tokens, handles empty input,
 *          closes the scanner when the stream is closed, and rejects a closed scanner
 * @run main ScannerWitupTest
 */

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testTokensBasic();
        testTokensWhitespaceOnlyIsEmpty();
        testTokensCustomDelimiter();
        testStreamCloseClosesScanner();
        testTokensOnClosedScannerThrows();
    }

    static List<String> collect(Stream<String> s) {
        List<String> out = new ArrayList<>();
        s.forEach(out::add);
        return out;
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }

    static void testTokensBasic() {
        Scanner sc = new Scanner(new StringReader("1 fish 2"));
        List<String> got = collect(sc.tokens());
        check(got.size() == 3, "expected 3 tokens but got " + got);
        check(got.get(0).equals("1"), "token 0 mismatch: " + got);
        check(got.get(1).equals("fish"), "token 1 mismatch: " + got);
        check(got.get(2).equals("2"), "token 2 mismatch: " + got);
    }

    static void testTokensWhitespaceOnlyIsEmpty() {
        Scanner sc = new Scanner(new StringReader("   \n\t  "));
        List<String> got = collect(sc.tokens());
        check(got.isEmpty(), "expected no tokens but got " + got);
    }

    static void testTokensCustomDelimiter() {
        Scanner sc = new Scanner(new StringReader("a, b ,c"));
        sc.useDelimiter("\\s*,\\s*");
        List<String> got = collect(sc.tokens());
        check(got.size() == 3, "expected 3 tokens but got " + got);
        check(got.get(0).equals("a"), "token 0 mismatch: " + got);
        check(got.get(1).equals("b"), "token 1 mismatch: " + got);
        check(got.get(2).equals("c"), "token 2 mismatch: " + got);
    }

    static void testStreamCloseClosesScanner() {
        Scanner sc = new Scanner(new StringReader("x y"));
        Stream<String> s = sc.tokens();
        s.close();
        boolean threw = false;
        try {
            sc.hasNext();
        } catch (IllegalStateException e) {
            threw = true;
        }
        check(threw, "scanner should be closed after closing the token stream");
    }

    static void testTokensOnClosedScannerThrows() {
        Scanner sc = new Scanner(new StringReader("x"));
        sc.close();
        boolean threw = false;
        try {
            sc.tokens();
        } catch (IllegalStateException e) {
            threw = true;
        }
        check(threw, "tokens() on a closed scanner should throw IllegalStateException");
    }
}
