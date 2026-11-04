/*
 * @test
 * @summary Deterministic tests for java.util.Scanner.hasNextLong()
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testHasNextLongWithValidLong();
        testHasNextLongWithInvalidInput();
        testHasNextLongWithDefaultRadixOutOfRange();
    }

    private static void testHasNextLongWithValidLong() {
        String input = "1234567890";
        Scanner scanner = new Scanner(input);
        boolean result = scanner.hasNextLong();
        if (!result) {
            throw new AssertionError("Expected hasNextLong() to return true for valid long input");
        }
        scanner.close();
    }

    private static void testHasNextLongWithInvalidInput() {
        String input = "notANumber";
        Scanner scanner = new Scanner(input);
        boolean result = scanner.hasNextLong();
        if (result) {
            throw new AssertionError("Expected hasNextLong() to return false for non-numeric input");
        }
        scanner.close();
    }

    private static void testHasNextLongWithDefaultRadixOutOfRange() {
        // The method hasNextLong() calls hasNextLong(defaultRadix).
        // If defaultRadix < 2 or > 36, hasNextLong() throws IllegalArgumentException.
        // We test this by creating a subclass to set defaultRadix to invalid values.

        // Since defaultRadix is private and no setter is public, we cannot set it directly.
        // Therefore, this test is omitted because no public API allows setting defaultRadix out of range.
        // This is consistent with the current code and no IllegalArgumentException is expected from public usage.
    }
}
