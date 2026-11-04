/*
 * @test
 * @summary Test java.util.Scanner.next(String) method with various patterns
 * @run main java.util.ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testNextWithValidPattern();
        testNextWithNoMatch();
        testNextWithEmptyPattern();
        testNextWithInvalidPattern();
    }

    private static void testNextWithValidPattern() {
        Scanner sc = new Scanner("abc123def");
        // pattern to match letters
        String result = sc.next("[a-zA-Z]+\d+");
        if (!"abc123".equals(result)) {
            throw new AssertionError("Expected 'abc123' but got '" + result + "'");
        }
        sc.close();
    }

    private static void testNextWithNoMatch() {
        Scanner sc = new Scanner("123 456");
        // pattern that does not match the next token
        try {
            sc.next("[a-zA-Z]+\d+");
            throw new AssertionError("Expected java.util.NoSuchElementException for no match");
        } catch (java.util.NoSuchElementException e) {
            // expected
        } finally {
            sc.close();
        }
    }

    private static void testNextWithEmptyPattern() {
        Scanner sc = new Scanner("abc");
        // empty pattern matches empty string at start
        String result = sc.next("");
        if (!"".equals(result)) {
            throw new AssertionError("Expected empty string but got '" + result + "'");
        }
        sc.close();
    }

    private static void testNextWithInvalidPattern() {
        Scanner sc = new Scanner("abc");
        try {
            sc.next("["); // invalid regex
            throw new AssertionError("Expected PatternSyntaxException for invalid regex");
        } catch (PatternSyntaxException e) {
            // expected
        } finally {
            sc.close();
        }
    }
}
