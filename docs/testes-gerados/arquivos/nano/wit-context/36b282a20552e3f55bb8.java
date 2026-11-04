/*
 * @test
 * @summary Tests for java.util.Scanner.findAll(Pattern pattern)
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.util.regex.*;
import java.util.stream.*;
import java.io.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testFindAllWithNullPattern();
        testFindAllWhenClosedAndPatternNotNull();
        testFindAllNormalOperation();
    }

    private static void testFindAllWithNullPattern() {
        Scanner scanner = new Scanner("sample text");
        try {
            scanner.findAll(null);
            System.err.println("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } finally {
            scanner.close();
        }
    }

    private static void testFindAllWhenClosedAndPatternNotNull() {
        Scanner scanner = new Scanner("sample text");
        Pattern pattern = Pattern.compile("\\w+");
        scanner.close();
        try {
            scanner.findAll(pattern);
            System.err.println("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }

    private static void testFindAllNormalOperation() {
        String input = "abc 123 def 456";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\\d+");
        try (Stream<MatchResult> results = scanner.findAll(pattern)) {
            List<String> matches = results.map(MatchResult::group).collect(Collectors.toList());
            if (!matches.equals(Arrays.asList("123", "456"))) {
                System.err.println("Unexpected matches: " + matches);
            }
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        } finally {
            scanner.close();
        }
    }
}
