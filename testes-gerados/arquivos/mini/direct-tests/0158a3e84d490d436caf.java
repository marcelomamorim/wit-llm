/*
 * @test
 * @summary Test java.util.Scanner.nextLine() method behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.StringReader;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testNextLineBasic();
        testNextLineEmptyLine();
        testNextLineNoLine();
    }

    private static void testNextLineBasic() {
        String input = "line1\nline2\nline3";
        Scanner scanner = new Scanner(new StringReader(input));
        String line1 = scanner.nextLine();
        if (!"line1".equals(line1)) {
            throw new AssertionError("Expected 'line1' but got: " + line1);
        }
        String line2 = scanner.nextLine();
        if (!"line2".equals(line2)) {
            throw new AssertionError("Expected 'line2' but got: " + line2);
        }
        String line3 = scanner.nextLine();
        if (!"line3".equals(line3)) {
            throw new AssertionError("Expected 'line3' but got: " + line3);
        }
        scanner.close();
    }

    private static void testNextLineEmptyLine() {
        String input = "first line\n\nthird line";
        Scanner scanner = new Scanner(new StringReader(input));
        String first = scanner.nextLine();
        if (!"first line".equals(first)) {
            throw new AssertionError("Expected 'first line' but got: " + first);
        }
        String empty = scanner.nextLine();
        if (!"".equals(empty)) {
            throw new AssertionError("Expected empty line but got: " + empty);
        }
        String third = scanner.nextLine();
        if (!"third line".equals(third)) {
            throw new AssertionError("Expected 'third line' but got: " + third);
        }
        scanner.close();
    }

    private static void testNextLineNoLine() {
        String input = "";
        Scanner scanner = new Scanner(new StringReader(input));
        boolean thrown = false;
        try {
            scanner.nextLine();
        } catch (NoSuchElementException e) {
            thrown = true;
        } finally {
            scanner.close();
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when no line is present");
        }
    }
}
