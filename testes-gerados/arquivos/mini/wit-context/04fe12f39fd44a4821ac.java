/*
 * @test
 * @summary Test java.util.Scanner.findInLine(Pattern) for expected exceptions and normal behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.io.StringReader;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testFindInLineWithClosedScanner();
        testFindInLineWithNullPattern();
        testFindInLineNormalBehavior();
    }

    static void testFindInLineWithClosedScanner() {
        Scanner sc = new Scanner("test input");
        sc.close();
        try {
            sc.findInLine(Pattern.compile("test"));
            throw new RuntimeException("Expected IllegalStateException not thrown when scanner is closed");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }

    static void testFindInLineWithNullPattern() {
        Scanner sc = new Scanner("some input");
        try {
            sc.findInLine(null);
            throw new RuntimeException("Expected NullPointerException not thrown when pattern is null");
        } catch (NullPointerException expected) {
            // Expected exception
        } finally {
            sc.close();
        }
    }

    static void testFindInLineNormalBehavior() {
        String input = "123 abc 456 def";
        Scanner sc = new Scanner(input);
        Pattern p = Pattern.compile("\\d+");
        String found = sc.findInLine(p);
        if (!"123".equals(found)) {
            throw new RuntimeException("Expected to find '123' but found: " + found);
        }
        // Test when pattern not found in line returns null
        Pattern notFoundPattern = Pattern.compile("xyz");
        String notFound = sc.findInLine(notFoundPattern);
        if (notFound != null) {
            throw new RuntimeException("Expected null when pattern not found but got: " + notFound);
        }
        sc.close();
    }
}
