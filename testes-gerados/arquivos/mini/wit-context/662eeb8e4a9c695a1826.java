/*
 * @test
 * @summary Test java.util.Scanner.match() method behavior including exception when no match is available
 * @run main java.util.ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testMatchThrowsWhenNoMatch();
        testMatchReturnsMatchResultWhenValid();
        System.out.println("ScannerWitupTest passed.");
    }

    private static void testMatchThrowsWhenNoMatch() {
        // Create a Scanner with input that does not perform any match
        Scanner scanner = new Scanner("");
        // No call to findInLine or similar, so matchValid is false internally
        try {
            scanner.match();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException ise) {
            // Expected exception
            String msg = ise.getMessage();
            if (msg == null || !msg.contains("No match result available")) {
                throw new RuntimeException("IllegalStateException message unexpected: " + msg);
            }
        } finally {
            scanner.close();
        }
    }

    private static void testMatchReturnsMatchResultWhenValid() {
        // Create a Scanner with input that matches a pattern
        String input = "abc123def";
        Scanner scanner = new Scanner(input);
        // Use findInLine to create a valid match
        String found = scanner.findInLine("(\\w+)(\\d+)(\\w+)");
        if (found == null) {
            scanner.close();
            throw new RuntimeException("findInLine failed to find expected pattern");
        }
        // Now match() should return a MatchResult
        java.util.regex.MatchResult result = scanner.match();
        if (result.groupCount() != 3) {
            scanner.close();
            throw new RuntimeException("MatchResult groupCount expected 3 but was " + result.groupCount());
        }
        if (!"abc123def".equals(result.group(0))) {
            scanner.close();
            throw new RuntimeException("MatchResult group(0) expected 'abc123def' but was '" + result.group(0) + "'");
        }
        if (!"abc".equals(result.group(1))) {
            scanner.close();
            throw new RuntimeException("MatchResult group(1) expected 'abc' but was '" + result.group(1) + "'");
        }
        if (!"123".equals(result.group(2))) {
            scanner.close();
            throw new RuntimeException("MatchResult group(2) expected '123' but was '" + result.group(2) + "'");
        }
        if (!"def".equals(result.group(3))) {
            scanner.close();
            throw new RuntimeException("MatchResult group(3) expected 'def' but was '" + result.group(3) + "'");
        }
        scanner.close();
    }
}
