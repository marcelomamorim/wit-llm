/*
 * @test
 * @summary Scanner.findAll(Pattern) yields matches lazily, rejects null patterns, and closes the scanner when the stream is closed
 * @run main ScannerWitupTest
 */
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScannerWitupTest {
    public static void main(String[] args) {
        testMatches();
        testNoMatch();
        testNullPattern();
        testStreamCloseClosesScanner();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testMatches() {
        Scanner sc = new Scanner("1 fish 2 fish");
        List<String> got = sc.findAll(Pattern.compile("\\d+"))
                .map(MatchResult::group)
                .collect(Collectors.toList());
        check(got.equals(Arrays.asList("1", "2")), "unexpected matches: " + got);
        sc.close();
    }

    static void testNoMatch() {
        Scanner sc = new Scanner("abc");
        long count = sc.findAll(Pattern.compile("\\d+")).count();
        check(count == 0, "expected no matches but got " + count);
        sc.close();
    }

    static void testNullPattern() {
        Scanner sc = new Scanner("1 2");
        try {
            sc.findAll(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        sc.close();
    }

    static void testStreamCloseClosesScanner() {
        Scanner sc = new Scanner("12");
        Stream<MatchResult> s = sc.findAll(Pattern.compile("\\d+"));
        s.close();
        try {
            sc.hasNext();
            throw new RuntimeException("expected IllegalStateException after stream close");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
