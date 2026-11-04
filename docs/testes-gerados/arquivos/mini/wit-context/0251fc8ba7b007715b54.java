/*
 * @test
 * @summary Test java.util.Scanner.findAll(String) for null argument and closed scanner
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.stream.Stream;
import java.util.regex.MatchResult;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.lang.IllegalStateException;
import java.util.Objects;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testFindAllNullPattern();
        testFindAllClosedScanner();
        testFindAllBasicFunctionality();
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
            scanner.findAll(".*");
            throw new RuntimeException("Expected IllegalStateException for closed scanner");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void testFindAllBasicFunctionality() {
        String input = "abc 123 def 456";
        Scanner scanner = new Scanner(input);
        Stream<MatchResult> matches = scanner.findAll("\\d+");
        int count = 0;
        for (MatchResult mr : (Iterable<MatchResult>) matches::iterator) {
            String match = mr.group();
            if (!match.matches("\\d+")) {
                throw new RuntimeException("Unexpected match: " + match);
            }
            count++;
        }
        if (count != 2) {
            throw new RuntimeException("Expected 2 matches, found " + count);
        }
        scanner.close();
    }
}
