/*
 * @test
 * @summary Test java.util.Scanner.hasNextShort() method behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testHasNextShortWithValidShort();
        testHasNextShortWithInvalidShort();
        testHasNextShortWithEmptyInput();
        testHasNextShortWithWhitespaceOnly();
    }

    private static void testHasNextShortWithValidShort() {
        String input = "12345";
        Scanner scanner = new Scanner(input);
        boolean result = scanner.hasNextShort();
        if (!result) {
            throw new AssertionError("Expected hasNextShort() to return true for valid short input");
        }
        short value = scanner.nextShort();
        if (value != 12345) {
            throw new AssertionError("Expected nextShort() to return 12345 but got " + value);
        }
        scanner.close();
    }

    private static void testHasNextShortWithInvalidShort() {
        String input = "40000"; // 40000 is outside short range
        Scanner scanner = new Scanner(input);
        boolean result = scanner.hasNextShort();
        if (result) {
            throw new AssertionError("Expected hasNextShort() to return false for out-of-range short input");
        }
        scanner.close();
    }

    private static void testHasNextShortWithEmptyInput() {
        String input = "";
        Scanner scanner = new Scanner(input);
        boolean result = scanner.hasNextShort();
        if (result) {
            throw new AssertionError("Expected hasNextShort() to return false for empty input");
        }
        scanner.close();
    }

    private static void testHasNextShortWithWhitespaceOnly() {
        String input = "   \t  \n  ";
        Scanner scanner = new Scanner(input);
        boolean result = scanner.hasNextShort();
        if (result) {
            throw new AssertionError("Expected hasNextShort() to return false for whitespace-only input");
        }
        scanner.close();
    }
}
