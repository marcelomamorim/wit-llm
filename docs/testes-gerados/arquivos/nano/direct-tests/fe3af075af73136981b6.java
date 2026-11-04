/*
 * @test
 * @summary Test the skip method of java.util.Scanner with various patterns.
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.util.regex.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testSkipWithMatchingPattern();
        testSkipWithNonMatchingPattern();
        testSkipWithNullPattern();
        testSkipWithPatternAtEnd();
    }

    private static void testSkipWithMatchingPattern() {
        String input = "abc123xyz";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\d+");
        scanner.skip(pattern);
        String remaining = scanner.next();
        assert remaining.equals("xyz") : "Expected 'xyz', got '" + remaining + "'";
        scanner.close();
    }

    private static void testSkipWithNonMatchingPattern() {
        String input = "abcxyz";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\d+");
        try {
            scanner.skip(pattern);
            assert false : "Expected NoSuchElementException";
        } catch (NoSuchElementException e) {
            // expected
        }
        scanner.close();
    }

    private static void testSkipWithNullPattern() {
        String input = "abc";
        Scanner scanner = new Scanner(input);
        try {
            scanner.skip(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
        scanner.close();
    }

    private static void testSkipWithPatternAtEnd() {
        String input = "abc123";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\d+");
        scanner.skip(pattern);
        String remaining = scanner.next();
        assert remaining.equals("") || remaining.equals("abc") : "Expected 'abc' or empty, got '" + remaining + "'";
        scanner.close();
    }
}
