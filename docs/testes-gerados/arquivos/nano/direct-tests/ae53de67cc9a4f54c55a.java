/*
 * @test
 * @summary Tests the skip method of java.util.Scanner for various patterns.
 * @run main ScannerWitupTest
 */

import java.util.Scanner;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testSkipWithSimplePattern();
        testSkipWithWhitespacePattern();
        testSkipWithNonMatchingPattern();
        testSkipWithEmptyPattern();
    }

    private static void testSkipWithSimplePattern() {
        String input = "abc123def";
        Scanner scanner = new Scanner(input);
        scanner.skip("abc");
        String remaining = scanner.nextLine();
        if (!remaining.equals("123def")) {
            throw new AssertionError("Expected '123def', but got: " + remaining);
        }
        scanner.close();
    }

    private static void testSkipWithWhitespacePattern() {
        String input = "   	
hello";
        Scanner scanner = new Scanner(input);
        scanner.skip("\s+");
        String remaining = scanner.nextLine();
        if (!remaining.equals("hello")) {
            throw new AssertionError("Expected 'hello', but got: " + remaining);
        }
        scanner.close();
    }

    private static void testSkipWithNonMatchingPattern() {
        String input = "abc";
        Scanner scanner = new Scanner(input);
        scanner.skip("xyz");
        String remaining = scanner.nextLine();
        if (!remaining.equals("abc")) {
            throw new AssertionError("Expected 'abc', but got: " + remaining);
        }
        scanner.close();
    }

    private static void testSkipWithEmptyPattern() {
        String input = "test";
        Scanner scanner = new Scanner(input);
        try {
            scanner.skip("");
            String remaining = scanner.nextLine();
            if (!remaining.equals("test")) {
                throw new AssertionError("Expected 'test', but got: " + remaining);
            }
        } catch (Exception e) {
            throw new AssertionError("skip with empty pattern should not throw", e);
        } finally {
            scanner.close();
        }
    }
}
