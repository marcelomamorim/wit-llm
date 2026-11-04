/*
 * @test
 * @summary Test for Scanner.findInLine(Pattern pattern) method.
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;
import java.util.regex.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testFindInLineWithNullPattern();
        testFindInLineWhenClosed();
        testFindInLineNormalBehavior();
    }

    private static void testFindInLineWithNullPattern() {
        Scanner scanner = null;
        try {
            scanner = new Scanner("sample input");
            try {
                scanner.findInLine(null);
                System.out.println("FAIL: Expected NullPointerException not thrown");
            } catch (NullPointerException e) {
                System.out.println("PASS: NullPointerException thrown as expected");
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private static void testFindInLineWhenClosed() {
        Scanner scanner = new Scanner("sample input");
        scanner.close();
        try {
            scanner.findInLine(Pattern.compile(".*"));
            System.out.println("FAIL: Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            System.out.println("PASS: IllegalStateException thrown as expected");
        }
    }

    private static void testFindInLineNormalBehavior() {
        String input = "line1\nline2\nline3";
        Scanner scanner = new Scanner(input);
        try {
            Pattern pattern = Pattern.compile("line\\d");
            String result = scanner.findInLine(pattern);
            if (result != null && result.contains("line")) {
                System.out.println("PASS: findInLine returned expected result: " + result);
            } else {
                System.out.println("FAIL: Unexpected result: " + result);
            }
        } finally {
            scanner.close();
        }
    }
}
