/*
 * @test
 * @summary Test java.util.Scanner.hasNext(String) method behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testHasNextWithMatchingPattern();
        testHasNextWithNonMatchingPattern();
        testHasNextWithEmptyInput();
        testHasNextWithInvalidPattern();
    }

    private static void testHasNextWithMatchingPattern() {
        Scanner scanner = new Scanner("hello world");
        boolean result = scanner.hasNext("hello");
        if (!result) {
            throw new AssertionError("Expected hasNext to return true for matching pattern");
        }
        scanner.close();
    }

    private static void testHasNextWithNonMatchingPattern() {
        Scanner scanner = new Scanner("hello world");
        boolean result = scanner.hasNext("bye");
        if (result) {
            throw new AssertionError("Expected hasNext to return false for non-matching pattern");
        }
        scanner.close();
    }

    private static void testHasNextWithEmptyInput() {
        Scanner scanner = new Scanner("");
        boolean result = scanner.hasNext(".*");
        if (result) {
            throw new AssertionError("Expected hasNext to return false for empty input");
        }
        scanner.close();
    }

    private static void testHasNextWithInvalidPattern() {
        Scanner scanner = new Scanner("input");
        try {
            scanner.hasNext("["); // invalid regex pattern
            throw new AssertionError("Expected PatternSyntaxException for invalid pattern");
        } catch (PatternSyntaxException e) {
            // expected
        } finally {
            scanner.close();
        }
    }
}
