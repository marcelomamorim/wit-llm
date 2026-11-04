/*
 * @test
 * @summary Test java.util.Scanner.findInLine(Pattern) method behavior
 * @run main java.util.ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testFindInLineReturnsMatchWithinLine();
        testFindInLineReturnsNullWhenNoMatchInLine();
        testFindInLineThrowsNPEOnNullPattern();
        testFindInLineReturnsNullOnEmptyLine();
    }

    private static void testFindInLineReturnsMatchWithinLine() {
        String input = "abc 123 def\nxyz 456";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\\d+");
        String result = scanner.findInLine(pattern);
        if (!"123".equals(result)) {
            throw new AssertionError("Expected '123' but got: " + result);
        }
        scanner.close();
    }

    private static void testFindInLineReturnsNullWhenNoMatchInLine() {
        String input = "abc def ghi\n123 456";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\\d+");
        String result = scanner.findInLine(pattern);
        if (result != null) {
            throw new AssertionError("Expected null but got: " + result);
        }
        scanner.close();
    }

    private static void testFindInLineThrowsNPEOnNullPattern() {
        Scanner scanner = new Scanner("some input");
        try {
            scanner.findInLine(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            scanner.close();
        }
    }

    private static void testFindInLineReturnsNullOnEmptyLine() {
        String input = "\n123";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile(".*");
        String result = scanner.findInLine(pattern);
        if (result != null) {
            throw new AssertionError("Expected null on empty line but got: " + result);
        }
        scanner.close();
    }
}
