/*
 * @test
 * @summary Test java.util.Scanner.skip(String) method for normal and closed states
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testSkipWithValidPattern();
        testSkipOnClosedScanner();
    }

    private static void testSkipWithValidPattern() {
        String input = "abc123def";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        // skip pattern "abc"
        Scanner result = scanner.skip("abc");
        if (result != scanner) {
            throw new AssertionError("skip should return the same Scanner instance");
        }
        // next token should start after "abc" which is "123def"
        String next = scanner.next();
        if (!"123def".equals(next)) {
            throw new AssertionError("Expected next token '123def' but got '" + next + "'");
        }
        scanner.close();
    }

    private static void testSkipOnClosedScanner() {
        String input = "test";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        scanner.close();
        try {
            scanner.skip("test");
            throw new AssertionError("Expected IllegalStateException when calling skip on closed Scanner");
        } catch (IllegalStateException expected) {
            // expected exception
        }
    }
}
