/*
 * @test
 * @summary Test Scanner.findAll(String) method for various patterns.
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testFindAllWithSimplePattern();
        testFindAllWithNoMatches();
        testFindAllWithComplexPattern();
    }

    private static void testFindAllWithSimplePattern() {
        String input = "apple banana cherry";
        Scanner scanner = new Scanner(input);
        try {
            Stream<MatchResult> results = scanner.findAll("\\w+");
            List<String> matches = results.map(MatchResult::group).collect(Collectors.toList());
            assert matches.size() == 3 : "Expected 3 matches";
            assert matches.contains("apple") : "Missing 'apple'";
            assert matches.contains("banana") : "Missing 'banana'";
            assert matches.contains("cherry") : "Missing 'cherry'";
        } finally {
            scanner.close();
        }
    }

    private static void testFindAllWithNoMatches() {
        String input = "1234 5678";
        Scanner scanner = new Scanner(input);
        try {
            Stream<MatchResult> results = scanner.findAll("[a-zA-Z]+");
            List<MatchResult> matches = results.collect(Collectors.toList());
            assert matches.isEmpty() : "Expected no matches";
        } finally {
            scanner.close();
        }
    }

    private static void testFindAllWithComplexPattern() {
        String input = "abc123 def456 ghi789";
        Scanner scanner = new Scanner(input);
        try {
            Stream<MatchResult> results = scanner.findAll("(\\w+)(\\d+)");
            List<MatchResult> matches = results.collect(Collectors.toList());
            assert matches.size() == 3 : "Expected 3 matches";
            for (MatchResult mr : matches) {
                String group1 = mr.group(1);
                String group2 = mr.group(2);
                assert group1.matches("\\w+") : "Group 1 should be word characters";
                assert group2.matches("\\d+") : "Group 2 should be digits";
            }
        } finally {
            scanner.close();
        }
    }
}
