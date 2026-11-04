/*
 * @test
 * @summary Test java.util.Scanner.hasNext(Pattern) method behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.Pattern;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testHasNextWithMatchingPattern();
        testHasNextWithNonMatchingPattern();
        testHasNextWithNullPattern();
    }

    private static void testHasNextWithMatchingPattern() throws Exception {
        String input = "abc123def";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\\d+"); // digits pattern
        boolean result = scanner.hasNext(pattern);
        if (!result) {
            throw new RuntimeException("Expected hasNext to return true for matching pattern");
        }
        scanner.close();
    }

    private static void testHasNextWithNonMatchingPattern() throws Exception {
        String input = "abcdef";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\\d+"); // digits pattern
        boolean result = scanner.hasNext(pattern);
        if (result) {
            throw new RuntimeException("Expected hasNext to return false for non-matching pattern");
        }
        scanner.close();
    }

    private static void testHasNextWithNullPattern() throws Exception {
        Scanner scanner = new Scanner("");
        try {
            scanner.hasNext(null);
            throw new RuntimeException("Expected NullPointerException for null pattern");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            scanner.close();
        }
    }
}
