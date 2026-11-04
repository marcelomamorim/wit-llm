/*
 * @test
 * @summary Test the Scanner.next(String pattern) method for various patterns.
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testNextWithExactMatch();
        testNextWithNoMatch();
        testNextWithMultipleMatches();
        testNextWithEmptyPattern();
        testNextWithNullPattern();
    }

    static void testNextWithExactMatch() {
        String input = "abc123xyz";
        Scanner scanner = new Scanner(input);
        String result = scanner.next("abc");
        assert result.equals("abc") : "Expected 'abc', got '" + result + "'";
        scanner.close();
    }

    static void testNextWithNoMatch() {
        String input = "abc123xyz";
        Scanner scanner = new Scanner(input);
        try {
            scanner.next("def");
            assert false : "Expected NoSuchElementException";
        } catch (NoSuchElementException e) {
            // expected
        }
        scanner.close();
    }

    static void testNextWithMultipleMatches() {
        String input = "foo bar baz";
        Scanner scanner = new Scanner(input);
        String result = scanner.next("foo");
        assert result.equals("foo") : "Expected 'foo', got '" + result + "'";
        scanner.close();
    }

    static void testNextWithEmptyPattern() {
        String input = "test";
        Scanner scanner = new Scanner(input);
        try {
            scanner.next("");
            assert false : "Expected PatternSyntaxException or similar";
        } catch (IllegalArgumentException e) {
            // expected
        }
        scanner.close();
    }

    static void testNextWithNullPattern() {
        String input = "test";
        Scanner scanner = new Scanner(input);
        try {
            scanner.next(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
        scanner.close();
    }
}
