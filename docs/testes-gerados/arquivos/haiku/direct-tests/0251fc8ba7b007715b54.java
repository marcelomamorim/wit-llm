/*
 * @test
 * @summary Verifies Scanner.findAll(String) null handling, closed-scanner behavior, match results and invalid patterns
 * @run main ScannerWitupTest
 */

import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public class ScannerWitupTest {

    public static void main(String[] args) throws Exception {
        testNullPatternThrowsNPE();
        testClosedScannerThrowsISE();
        testFindAllReturnsAllGroups();
        testFindAllReturnsPositions();
        testFindAllNoMatchReturnsEmpty();
        testInvalidPatternThrowsPatternSyntaxException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullPatternThrowsNPE() {
        Scanner sc = new Scanner("abc");
        try {
            sc.findAll((String) null);
            throw new RuntimeException("Expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testClosedScannerThrowsISE() {
        Scanner sc = new Scanner("abc");
        sc.close();
        try {
            sc.findAll("a");
            throw new RuntimeException("Expected IllegalStateException");
        } catch (IllegalStateException expected) {
        }
    }

    static void testFindAllReturnsAllGroups() {
        List<String> groups = new Scanner("1 fish 2 fish")
                .findAll("(\\d+) fish")
                .map(r -> r.group(1))
                .collect(Collectors.toList());
        check(groups.size() == 2, "Expected 2 matches but got " + groups.size());
        check(groups.get(0).equals("1"), "First group mismatch: " + groups.get(0));
        check(groups.get(1).equals("2"), "Second group mismatch: " + groups.get(1));
    }

    static void testFindAllReturnsPositions() {
        List<Integer> starts = new Scanner("abcb")
                .findAll("b")
                .map(MatchResult::start)
                .collect(Collectors.toList());
        check(starts.size() == 2, "Expected 2 matches but got " + starts.size());
        check(starts.get(0) == 1, "First start mismatch: " + starts.get(0));
        check(starts.get(1) == 3, "Second start mismatch: " + starts.get(1));
    }

    static void testFindAllNoMatchReturnsEmpty() {
        long count = new Scanner("abc").findAll("\\d").count();
        check(count == 0, "Expected 0 matches but got " + count);
    }

    static void testInvalidPatternThrowsPatternSyntaxException() {
        Scanner sc = new Scanner("abc");
        try {
            sc.findAll("(");
            throw new RuntimeException("Expected PatternSyntaxException");
        } catch (PatternSyntaxException expected) {
        }
    }
}
