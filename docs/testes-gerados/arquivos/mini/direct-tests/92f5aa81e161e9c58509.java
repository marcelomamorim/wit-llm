/*
 * @test
 * @summary Test java.util.Scanner.next(Pattern) method behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.NoSuchElementException;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testNextWithNullPattern();
        testNextWithMatchingPattern();
        testNextWithNoMatchThrows();
    }

    private static void testNextWithNullPattern() {
        Scanner sc = new Scanner("");
        try {
            sc.next(null);
            throw new AssertionError("Expected NullPointerException for null pattern");
        } catch (NullPointerException expected) {
        } finally {
            sc.close();
        }
    }

    private static void testNextWithMatchingPattern() {
        String input = "abc123def";
        Scanner sc = new Scanner(input);
        Pattern pattern = Pattern.compile("\\w+"); // matches word characters
        String token = sc.next(pattern);
        if (!"abc123def".equals(token)) {
            throw new AssertionError("Expected token 'abc123def' but got '" + token + "'");
        }
        sc.close();
    }

    private static void testNextWithNoMatchThrows() {
        String input = "123 456";
        Scanner sc = new Scanner(input);
        Pattern pattern = Pattern.compile("[a-zA-Z]+?"); // pattern that does not match input
        try {
            sc.next(pattern);
            throw new AssertionError("Expected NoSuchElementException when no match found");
        } catch (NoSuchElementException expected) {
        } finally {
            sc.close();
        }
    }
}
