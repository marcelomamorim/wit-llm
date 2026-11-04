/* @test    @summary Test for Scanner.nextLine() method to ensure it returns the next line or throws NoSuchElementException if no line is available.    @run main ScannerWitupTest */

import java.util.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testNextLineWithContent();
        testNextLineWithoutContent();
    }

    private static void testNextLineWithContent() {
        String input = "First line\nSecond line";
        Scanner scanner = new Scanner(input);
        try {
            String line1 = scanner.nextLine();
            String line2 = scanner.nextLine();
            if (!"First line".equals(line1)) {
                throw new AssertionError("Expected 'First line', got: " + line1);
            }
            if (!"Second line".equals(line2)) {
                throw new AssertionError("Expected 'Second line', got: " + line2);
            }
        } finally {
            scanner.close();
        }
    }

    private static void testNextLineWithoutContent() {
        String input = "";
        Scanner scanner = new Scanner(input);
        try {
            boolean exceptionThrown = false;
            try {
                scanner.nextLine();
            } catch (NoSuchElementException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected NoSuchElementException to be thrown");
            }
        } finally {
            scanner.close();
        }
    }
}
