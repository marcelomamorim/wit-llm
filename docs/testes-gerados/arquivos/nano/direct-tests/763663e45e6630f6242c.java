/*
 * @test
 * @summary Test for Scanner.hasNext(Pattern pattern)
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.util.regex.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testHasNextWithMatchingPattern();
        testHasNextWithNonMatchingPattern();
        testHasNextWithNullPattern();
    }

    private static void testHasNextWithMatchingPattern() {
        String input = "abc 123 xyz";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\d+");
        boolean result = scanner.hasNext(pattern);
        if (!result) {
            throw new AssertionError("Expected hasNext to return true for matching pattern");
        }
        scanner.close();
    }

    private static void testHasNextWithNonMatchingPattern() {
        String input = "abc xyz";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\d+");
        boolean result = scanner.hasNext(pattern);
        if (result) {
            throw new AssertionError("Expected hasNext to return false for non-matching pattern");
        }
        scanner.close();
    }

    private static void testHasNextWithNullPattern() {
        String input = "abc";
        Scanner scanner = new Scanner(input);
        try {
            scanner.hasNext(null);
            throw new AssertionError("Expected NullPointerException for null pattern");
        } catch (NullPointerException e) {
            // expected
        } finally {
            scanner.close();
        }
    }
}
