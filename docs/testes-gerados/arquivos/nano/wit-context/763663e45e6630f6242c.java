/*
 * @test
 * @summary Tests for java.util.Scanner.hasNext(Pattern pattern)
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.util.regex.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testHasNextWithValidPattern();
        testHasNextWithNullPattern();
        testHasNextWithNullPatternAndOpenScanner();
    }

    private static void testHasNextWithValidPattern() {
        String input = "abc 123";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\"\d+\"");
        try {
            boolean result = scanner.hasNext(pattern);
            System.out.println("hasNext with valid pattern: " + result);
        } catch (Exception e) {
            System.err.println("Unexpected exception in testHasNextWithValidPattern: " + e);
        } finally {
            scanner.close();
        }
    }

    private static void testHasNextWithNullPattern() {
        String input = "abc 123";
        Scanner scanner = new Scanner(input);
        try {
            scanner.hasNext(null);
            System.err.println("Expected NullPointerException was not thrown in testHasNextWithNullPattern");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException correctly thrown in testHasNextWithNullPattern");
        } catch (Exception e) {
            System.err.println("Unexpected exception in testHasNextWithNullPattern: " + e);
        } finally {
            scanner.close();
        }
    }

    private static void testHasNextWithNullPatternAndOpenScanner() {
        String input = "abc 123";
        Scanner scanner = new Scanner(input);
        // Simulate closed scanner by closing it before calling hasNext
        scanner.close();
        try {
            scanner.hasNext(Pattern.compile("\"\d+\""));
            System.err.println("Expected IllegalStateException was not thrown in testHasNextWithNullPatternAndOpenScanner");
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException correctly thrown in testHasNextWithNullPatternAndOpenScanner");
        } catch (Exception e) {
            System.err.println("Unexpected exception in testHasNextWithNullPatternAndOpenScanner: " + e);
        }
    }
}
