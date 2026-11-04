/*
 * @test
 * @summary Test java.util.Scanner.findAll(Pattern) for null argument and closed scanner
 * @run main java.util.ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.regex.MatchResult;
import java.io.IOException;
import java.io.InputStream;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testFindAllNullPattern();
        testFindAllClosedScanner();
        testFindAllBasicStream();
    }

    private static void testFindAllNullPattern() {
        Scanner scanner = new Scanner("");
        try {
            scanner.findAll(null);
            throw new RuntimeException("Expected NullPointerException for null pattern");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            scanner.close();
        }
    }

    private static void testFindAllClosedScanner() {
        Scanner scanner = new Scanner("");
        scanner.close();
        try {
            scanner.findAll(Pattern.compile(".*"));
            throw new RuntimeException("Expected IllegalStateException for closed scanner");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void testFindAllBasicStream() {
        String input = "abc123def456";
        Scanner scanner = new Scanner(input);
        Pattern pattern = Pattern.compile("\\d+");
        Stream<MatchResult> stream = scanner.findAll(pattern);
        int count = 0;
        for (MatchResult mr : (Iterable<MatchResult>) stream::iterator) {
            String match = mr.group();
            if (!match.matches("\\d+")) {
                throw new RuntimeException("Unexpected match: " + match);
            }
            count++;
        }
        if (count != 2) {
            throw new RuntimeException("Expected 2 matches, found " + count);
        }
        stream.close();
        scanner.close();
    }
}
