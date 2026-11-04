/*
 * @test
 * @summary Test java.util.Scanner.skip(Pattern) for exception and normal behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.NoSuchElementException;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testSkipWithClosedScanner();
        testSkipWithNullPattern();
        testSkipWithNoMatch();
        testSkipWithMatch();
    }

    static void testSkipWithClosedScanner() {
        Scanner sc = new Scanner("");
        sc.close();
        try {
            sc.skip(Pattern.compile(".*"));
            throw new RuntimeException("Expected IllegalStateException for closed scanner");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void testSkipWithNullPattern() {
        Scanner sc = new Scanner("");
        try {
            sc.skip(null);
            throw new RuntimeException("Expected NullPointerException for null pattern");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            sc.close();
        }
    }

    static void testSkipWithNoMatch() {
        Scanner sc = new Scanner("abc");
        try {
            sc.skip(Pattern.compile("def"));
            throw new RuntimeException("Expected NoSuchElementException when pattern not found");
        } catch (NoSuchElementException expected) {
            // expected
        } finally {
            sc.close();
        }
    }

    static void testSkipWithMatch() {
        Scanner sc = new Scanner("abc123def");
        Scanner result = sc.skip(Pattern.compile("abc123"));
        if (result != sc) {
            throw new RuntimeException("skip should return the same Scanner instance");
        }
        // After skip, next token should start after matched pattern
        if (!sc.hasNext()) {
            throw new RuntimeException("Expected more input after skip");
        }
        String next = sc.next();
        if (!"def".equals(next)) {
            throw new RuntimeException("Expected next token to be 'def' but was '" + next + "'");
        }
        sc.close();
    }
}
