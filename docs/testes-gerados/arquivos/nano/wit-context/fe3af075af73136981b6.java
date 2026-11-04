/*
 * @test
 * @summary Tests for java.util.Scanner.skip(Pattern)
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testSkipWithNullPattern();
        testSkipWhenClosedThrowsIllegalStateException();
        testSkipWithValidPattern();
    }

    private static void testSkipWithNullPattern() {
        Scanner scanner = null;
        try {
            scanner = new Scanner("test input");
            try {
                scanner.skip(null);
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

    private static void testSkipWhenClosedThrowsIllegalStateException() {
        Scanner scanner = new Scanner("test input");
        scanner.close();
        try {
            scanner.skip(Pattern.compile("\s"));
            System.out.println("FAIL: Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            System.out.println("PASS: IllegalStateException thrown as expected");
        }
    }

    private static void testSkipWithValidPattern() {
        String input = "abc 123 def";
        Scanner scanner = new Scanner(input);
        try {
            // Skip non-digit characters
            scanner.skip(Pattern.compile("[^0-9]+"));
            String remaining = scanner.nextLine();
            if (remaining.startsWith(" 123 def")) {
                System.out.println("PASS: skip with valid pattern works as expected");
            } else {
                System.out.println("FAIL: Unexpected remaining input after skip: " + remaining);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        } finally {
            scanner.close();
        }
    }
}
