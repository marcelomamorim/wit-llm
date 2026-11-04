/*
 * @test
 * @summary Test java.util.Scanner.findAll(String) method behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.stream.Stream;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testFindAllWithValidPattern();
        testFindAllWithNullPattern();
    }

    private static void testFindAllWithValidPattern() {
        String input = "abc123def456ghi789";
        Scanner scanner = new Scanner(input);
        Stream<MatchResult> matches = scanner.findAll("\\d+");
        // Collect matched groups
        StringBuilder sb = new StringBuilder();
        matches.forEach(mr -> sb.append(mr.group()).append(","));
        scanner.close();
        String result = sb.toString();
        // Expect digits sequences separated by commas
        if (!result.equals("123,456,789,")) {
            throw new AssertionError("Expected '123,456,789,' but got: " + result);
        }
    }

    private static void testFindAllWithNullPattern() {
        Scanner scanner = new Scanner("");
        try {
            scanner.findAll(null);
            throw new AssertionError("Expected NullPointerException for null pattern");
        } catch (NullPointerException npe) {
            // expected
        } finally {
            scanner.close();
        }
    }
}
