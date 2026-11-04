/*
 * @test
 * @summary Verify Scanner.findInLine(String) matches within the current line, returns null when there is no match, advances the position past the match and throws IllegalStateException when the scanner is closed
 * @run main ScannerWitupTest
 */

import java.io.StringReader;
import java.util.Scanner;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testMatchReturnsToken();
        testNoMatchReturnsNull();
        testMatchAdvancesPosition();
        testClosedThrowsIllegalStateException();
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void testMatchReturnsToken() {
        Scanner sc = new Scanner(new StringReader("abc 123 def"));
        String result = sc.findInLine("\\d+");
        check("123".equals(result), "expected 123 but was " + result);
        sc.close();
    }

    private static void testNoMatchReturnsNull() {
        Scanner sc = new Scanner(new StringReader("abc 123 def"));
        String result = sc.findInLine("zzz");
        check(result == null, "expected null but was " + result);
        sc.close();
    }

    private static void testMatchAdvancesPosition() {
        Scanner sc = new Scanner(new StringReader("abc 123 def"));
        String first = sc.findInLine("\\d+");
        check("123".equals(first), "expected 123 but was " + first);
        String next = sc.next();
        check("def".equals(next), "expected def after match but was " + next);
        sc.close();
    }

    private static void testClosedThrowsIllegalStateException() {
        Scanner sc = new Scanner(new StringReader("abc 123 def"));
        sc.close();
        try {
            sc.findInLine("\\d+");
            throw new RuntimeException("expected IllegalStateException on closed scanner");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
