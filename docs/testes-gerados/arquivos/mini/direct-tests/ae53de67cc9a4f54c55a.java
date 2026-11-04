/*
 * @test
 * @summary Test java.util.Scanner.skip(String) method basic behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testSkipWithValidPattern();
        testSkipWithInvalidPattern();
    }

    private static void testSkipWithValidPattern() {
        String input = "abc123def";
        Scanner scanner = new Scanner(input);
        // skip letters
        scanner.skip("[a-z]+\");
        // next token should start with digits
        String token = scanner.next();
        if (!token.startsWith("123")) {
            throw new AssertionError("Expected token starting with '123', got: " + token);
        }
        scanner.close();
    }

    private static void testSkipWithInvalidPattern() {
        Scanner scanner = new Scanner("");
        boolean caught = false;
        try {
            scanner.skip("[unclosed");
        } catch (PatternSyntaxException e) {
            caught = true;
        } finally {
            scanner.close();
        }
        if (!caught) {
            throw new AssertionError("Expected PatternSyntaxException for invalid regex pattern");
        }
    }
}
