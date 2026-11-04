/*
 * @test
 * @summary Tests for java.util.Scanner.next(Pattern pattern)
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.Pattern;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testNextWithNullPattern();
        testNextWhenClosed();
        testNextWithInvalidPattern();
    }

    private static void testNextWithNullPattern() {
        Scanner scanner = new Scanner("test input");
        try {
            scanner.next(null);
            System.out.println("FAIL: Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } finally {
            scanner.close();
        }
    }

    private static void testNextWhenClosed() {
        Scanner scanner = new Scanner("test input");
        scanner.close();
        try {
            scanner.next(Pattern.compile("\w+"));
            System.out.println("FAIL: Expected IllegalStateException");
        } catch (IllegalStateException e) {
            System.out.println("PASS: IllegalStateException thrown as expected");
        }
    }

    private static void testNextWithInvalidPattern() {
        Scanner scanner = new Scanner("test input");
        Pattern invalidPattern = null;
        try {
            scanner.next(invalidPattern);
            System.out.println("FAIL: Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } finally {
            scanner.close();
        }
    }
}
