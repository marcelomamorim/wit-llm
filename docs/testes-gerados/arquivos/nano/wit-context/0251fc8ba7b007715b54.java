/*
 * @test
 * @summary Test for Scanner.findAll(String)
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.util.regex.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testFindAllWithNullPattern();
        testFindAllWithClosedScanner();
    }

    private static void testFindAllWithNullPattern() {
        Scanner scanner = new Scanner("sample text");
        try {
            scanner.findAll(null);
            System.err.println("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } finally {
            scanner.close();
        }
    }

    private static void testFindAllWithClosedScanner() {
        Scanner scanner = new Scanner("sample text");
        scanner.close();
        try {
            scanner.findAll("\w+");
            System.err.println("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
