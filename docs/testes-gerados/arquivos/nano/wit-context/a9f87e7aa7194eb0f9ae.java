/*
 * @test
 * @summary Test for Scanner.next(String pattern) method behavior.
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testNextWithPatternWhenClosed();
        testNextWithPatternNormal();
    }

    private static void testNextWithPatternWhenClosed() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new StringReader("test"));
            scanner.close();
            String pattern = ".*";
            String result = scanner.next(pattern);
            System.out.println("Unexpected success: " + result);
        } catch (IllegalStateException e) {
            System.out.println("Caught expected IllegalStateException when scanner is closed.");
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private static void testNextWithPatternNormal() {
        Scanner scanner = null;
        try {
            String input = "abc123xyz";
            scanner = new Scanner(new StringReader(input));
            String pattern = "(\\w+)(\\d+)(\\w+)";
            String result = scanner.next(pattern);
            System.out.println("Result of next with pattern: " + result);
            MatchResult match = scanner.match();
            System.out.println("Match groups:");
            for (int i = 1; i <= match.groupCount(); i++) {
                System.out.println("Group " + i + ": " + match.group(i));
            }
        } catch (Exception e) {
            System.out.println("Unexpected exception during normal operation: " + e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
