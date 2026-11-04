/*
 * @test
 * @summary Test java.util.Scanner.findAll(Pattern) method behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.MatchResult;
import java.util.stream.Stream;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testFindAllWithValidPattern();
        testFindAllWithNullPattern();
        testFindAllStreamCloseClosesScanner();
    }

    private static void testFindAllWithValidPattern() {
        String input = "one 1 two 2 three 3";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\\d");
        Stream<MatchResult> results = scanner.findAll(pattern);

        int count = 0;
        for (MatchResult mr : (Iterable<MatchResult>) results::iterator) {
            String match = mr.group();
            if (!match.matches("\\d")) {
                throw new AssertionError("Match does not match digit pattern: " + match);
            }
            count++;
        }
        if (count != 3) {
            throw new AssertionError("Expected 3 matches, found " + count);
        }
        results.close();
    }

    private static void testFindAllWithNullPattern() {
        Scanner scanner = new Scanner("");
        try {
            scanner.findAll(null);
            throw new AssertionError("Expected NullPointerException for null pattern");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            scanner.close();
        }
    }

    private static void testFindAllStreamCloseClosesScanner() {
        String input = "abc 123 def 456";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\\d+");
        Stream<MatchResult> stream = scanner.findAll(pattern);

        // The stream should close the scanner when stream.close() is called
        stream.close();

        try {
            scanner.hasNext();
            throw new AssertionError("Expected IllegalStateException after scanner closed by stream");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
