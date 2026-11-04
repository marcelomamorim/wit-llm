/*
 * @test
 * @summary Tests for java.util.Scanner.findWithinHorizon(Pattern, int)
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;
import java.util.regex.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testNullPattern();
        testNegativeHorizon();
        testClosedScanner();
        testNormalOperation();
    }

    private static void testNullPattern() {
        Scanner scanner = new Scanner("sample input");
        try {
            scanner.findWithinHorizon(null, 10);
            // Expect NullPointerException
            System.err.println("Failed: Expected NullPointerException for null pattern");
        } catch (NullPointerException e) {
            // Expected
        } finally {
            scanner.close();
        }
    }

    private static void testNegativeHorizon() {
        Scanner scanner = new Scanner("sample input");
        Pattern pattern = Pattern.compile("\d+");
        try {
            scanner.findWithinHorizon(pattern, -1);
            System.err.println("Failed: Expected IllegalArgumentException for negative horizon");
        } catch (IllegalArgumentException e) {
            // Expected
        } finally {
            scanner.close();
        }
    }

    private static void testClosedScanner() {
        Scanner scanner = new Scanner("sample input");
        scanner.close();
        try {
            scanner.findWithinHorizon(Pattern.compile("\d+"), 10);
            System.err.println("Failed: Expected IllegalStateException for closed scanner");
        } catch (IllegalStateException e) {
            // Expected
        }
    }

    private static void testNormalOperation() {
        String input = "abc 123 def";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\d+");
        String result = scanner.findWithinHorizon(pattern, 100);
        if (result == null || !result.equals("123")) {
            System.err.println("Failed: Expected to find '123' in input");
        }
        scanner.close();
    }
}
