/*
 * @test
 * @summary Test Scanner.findWithinHorizon method for expected behavior and edge cases
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;
import java.util.regex.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testFindWithinHorizonWithMatchingPattern();
        testFindWithinHorizonWithNoMatch();
        testFindWithinHorizonWithNullPattern();
        testFindWithinHorizonWithNegativeHorizon();
        testFindWithinHorizonWithInputRead();
    }

    static void testFindWithinHorizonWithMatchingPattern() {
        String input = "abc123xyz";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\\d+");
        String result = scanner.findWithinHorizon(pattern, 100);
        assert result != null : "Expected match, but got null";
        assert result.equals("123") : "Expected '123', but got '" + result + "'";
        scanner.close();
    }

    static void testFindWithinHorizonWithNoMatch() {
        String input = "abcdef";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\\d+");
        String result = scanner.findWithinHorizon(pattern, 100);
        assert result == null : "Expected null, but got '" + result + "'";
        scanner.close();
    }

    static void testFindWithinHorizonWithNullPattern() {
        String input = "test";
        Scanner scanner = new Scanner(input);
        try {
            scanner.findWithinHorizon(null, 10);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        } finally {
            scanner.close();
        }
    }

    static void testFindWithinHorizonWithNegativeHorizon() {
        String input = "test";
        Scanner scanner = new Scanner(input);
        try {
            scanner.findWithinHorizon(Pattern.compile("."), -1);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // expected
        } finally {
            scanner.close();
        }
    }

    static void testFindWithinHorizonWithInputRead() {
        String input = "hello 123 world";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\\d+");
        String firstMatch = scanner.findWithinHorizon(pattern, 100);
        assert firstMatch != null && firstMatch.equals("123") : "Expected '123', but got '" + firstMatch + "'";
        // Read remaining input
        String remaining = scanner.nextLine();
        assert remaining.contains("world") : "Remaining input should contain 'world'";
        scanner.close();
    }
}
