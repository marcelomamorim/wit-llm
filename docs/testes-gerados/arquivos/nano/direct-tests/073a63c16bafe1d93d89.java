/*
 * @test
 * @summary Test the findInLine method of java.util.Scanner for various patterns.
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testFindInLineWithMatchingPattern();
        testFindInLineWithNonMatchingPattern();
        testFindInLineWithEmptyInput();
        testFindInLineWithNullPattern();
    }

    private static void testFindInLineWithMatchingPattern() {
        String input = "abc 123 def";
        Scanner scanner = new Scanner(input);
        String result = scanner.findInLine("(\\d+)");
        if ("123".equals(result)) {
            System.out.println("PASS: Matching pattern found expected result.");
        } else {
            System.out.println("FAIL: Matching pattern did not find expected result.");
        }
        scanner.close();
    }

    private static void testFindInLineWithNonMatchingPattern() {
        String input = "abc def";
        Scanner scanner = new Scanner(input);
        String result = scanner.findInLine("(\\d+)");
        if (result == null) {
            System.out.println("PASS: Non-matching pattern correctly returned null.");
        } else {
            System.out.println("FAIL: Non-matching pattern did not return null.");
        }
        scanner.close();
    }

    private static void testFindInLineWithEmptyInput() {
        String input = "";
        Scanner scanner = new Scanner(input);
        String result = scanner.findInLine("(\\w+)");
        if (result == null) {
            System.out.println("PASS: Empty input correctly returned null.");
        } else {
            System.out.println("FAIL: Empty input did not return null.");
        }
        scanner.close();
    }

    private static void testFindInLineWithNullPattern() {
        String input = "test";
        Scanner scanner = new Scanner(input);
        try {
            scanner.findInLine(null);
            System.out.println("FAIL: Null pattern did not throw NullPointerException.");
        } catch (NullPointerException e) {
            System.out.println("PASS: Null pattern threw NullPointerException as expected.");
        } finally {
            scanner.close();
        }
    }
}
