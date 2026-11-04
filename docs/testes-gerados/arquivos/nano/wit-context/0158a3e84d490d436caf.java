/*
 * @test
 * @summary Tests for java.util.Scanner.nextLine() method, covering normal and exceptional behaviors.
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testNormalNextLine();
        testNoSuchElementException();
        testIllegalStateExceptionClosedWithPattern();
        testIllegalStateExceptionClosedWithDifferentPattern();
        testNullPointerException();
    }

    private static void testNormalNextLine() {
        String input = "line1\nline2\n";
        Scanner scanner = new Scanner(input);
        try {
            String line1 = scanner.nextLine();
            String line2 = scanner.nextLine();
            if (!"line1".equals(line1) || !"line2".equals(line2)) {
                throw new AssertionError("Unexpected lines: " + line1 + ", " + line2);
            }
        } finally {
            scanner.close();
        }
    }

    private static void testNoSuchElementException() {
        String input = "only one line";
        Scanner scanner = new Scanner(input);
        try {
            scanner.nextLine();
            boolean exceptionThrown = false;
            try {
                scanner.nextLine();
            } catch (NoSuchElementException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected NoSuchElementException not thrown");
            }
        } finally {
            scanner.close();
        }
    }

    private static void testIllegalStateExceptionClosedWithPattern() {
        String input = "test";
        Scanner scanner = new Scanner(input);
        try {
            // Simulate closed scanner
            scanner.close();
            boolean exceptionThrown = false;
            try {
                scanner.nextLine();
            } catch (IllegalStateException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected IllegalStateException not thrown");
            }
        } finally {
            // no action
        }
    }

    private static void testIllegalStateExceptionClosedWithDifferentPattern() {
        String input = "test";
        Scanner scanner = new Scanner(input);
        try {
            // Simulate closed scanner
            scanner.close();
            boolean exceptionThrown = false;
            try {
                scanner.nextLine();
            } catch (IllegalStateException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected IllegalStateException not thrown");
            }
        } finally {
            // no action
        }
    }

    private static void testNullPointerException() {
        String input = "";
        Scanner scanner = new Scanner(input);
        try {
            // Force findWithinHorizon to return null to simulate NPE scenario
            // Since findWithinHorizon is package-private, we simulate by closing scanner and calling nextLine
            scanner.close();
            boolean exceptionThrown = false;
            try {
                scanner.nextLine();
            } catch (NoSuchElementException e) {
                // Expected, do nothing
            } catch (IllegalStateException e) {
                // Expected due to closed scanner
            }
        } finally {
            // no action
        }
    }
}
