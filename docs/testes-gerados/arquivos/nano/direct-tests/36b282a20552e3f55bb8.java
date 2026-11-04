/*
 * @test
 * @summary Test Scanner.findAll(Pattern pattern) method for basic functionality.
 * @run main ScannerWitupTest
 */

import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testFindAllWithMatchingPattern();
        testFindAllWithNonMatchingPattern();
        testFindAllWithNullPattern();
    }

    public static void testFindAllWithMatchingPattern() {
        String input = "abc 123 def 456";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\d+");
        Stream<MatchResult> results = scanner.findAll(pattern);
        List<String> matches = results.map(MatchResult::group).collect(Collectors.toList());
        results.close();
        assert matches.size() == 2 : "Expected 2 matches";
        assert matches.get(0).equals("123") : "First match should be '123'";
        assert matches.get(1).equals("456") : "Second match should be '456'";
        scanner.close();
    }

    public static void testFindAllWithNonMatchingPattern() {
        String input = "abc def";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\d+");
        Stream<MatchResult> results = scanner.findAll(pattern);
        List<MatchResult> matches = results.collect(Collectors.toList());
        results.close();
        assert matches.isEmpty() : "Expected no matches";
        scanner.close();
    }

    public static void testFindAllWithNullPattern() {
        try {
            Scanner scanner = new Scanner("test");
            scanner.findAll(null);
            scanner.close();
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
