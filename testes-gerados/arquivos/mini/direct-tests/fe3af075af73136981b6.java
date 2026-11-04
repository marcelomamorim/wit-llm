/*
 * @test
 * @summary Test java.util.Scanner.skip(Pattern) method behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.NoSuchElementException;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testSkipWithNullPattern();
        testSkipWithMatchingPattern();
        testSkipWithNoMatchThrows();
    }

    private static void testSkipWithNullPattern() {
        Scanner sc = new Scanner("");
        try {
            sc.skip(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        } finally {
            sc.close();
        }
    }

    private static void testSkipWithMatchingPattern() {
        String input = "abc123def";
        Scanner sc = new Scanner(input);
        Pattern p = Pattern.compile("abc123");
        Scanner returned = sc.skip(p);
        if (returned != sc) {
            throw new RuntimeException("skip should return the same Scanner instance");
        }
        // After skipping "abc123", next token should start at "def"
        String next = sc.next();
        if (!"def".equals(next)) {
            throw new RuntimeException("Expected next token 'def', got: " + next);
        }
        sc.close();
    }

    private static void testSkipWithNoMatchThrows() {
        String input = "xyz";
        Scanner sc = new Scanner(input);
        Pattern p = Pattern.compile("abc");
        try {
            sc.skip(p);
            throw new RuntimeException("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException nsee) {
            // expected
        } finally {
            sc.close();
        }
    }
}
