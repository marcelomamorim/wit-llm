/*
 * @test
 * @summary Test the findInLine method of java.util.Scanner for various patterns.
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testFindInLineWithPattern();
        testFindInLineWithNoMatch();
        testFindInLineWithNullPattern();
        testFindInLineWithEmptyInput();
    }

    private static void testFindInLineWithPattern() {
        String input = "abc 123 def";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\\d+");
        String result = scanner.findInLine(pattern);
        if (!"123".equals(result)) {
            throw new AssertionError("Expected '123', got: " + result);
        }
        scanner.close();
    }

    private static void testFindInLineWithNoMatch() {
        String input = "abc def";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\\d+");
        String result = scanner.findInLine(pattern);
        if (result != null) {
            throw new AssertionError("Expected null, got: " + result);
        }
        scanner.close();
    }

    private static void testFindInLineWithNullPattern() {
        String input = "abc 123";
        Scanner scanner = new Scanner(input);
        try {
            scanner.findInLine(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        } finally {
            scanner.close();
        }
    }

    private static void testFindInLineWithEmptyInput() {
        String input = "";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile(".+");
        String result = scanner.findInLine(pattern);
        if (result != null) {
            throw new AssertionError("Expected null for empty input, got: " + result);
        }
        scanner.close();
    }
}
