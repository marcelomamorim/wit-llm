/*
 * @test
 * @summary Test java.util.Scanner.hasNextLong() method behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testHasNextLongWithValidLong();
        testHasNextLongWithInvalidLong();
        testHasNextLongWithEmptyInput();
        testHasNextLongWithLeadingWhitespace();
        testHasNextLongWithNegativeLong();
    }

    private static void testHasNextLongWithValidLong() {
        Scanner sc = new Scanner("1234567890");
        boolean result = sc.hasNextLong();
        sc.close();
        if (!result) {
            throw new RuntimeException("Expected hasNextLong() to return true for valid long input");
        }
    }

    private static void testHasNextLongWithInvalidLong() {
        Scanner sc = new Scanner("notANumber");
        boolean result = sc.hasNextLong();
        sc.close();
        if (result) {
            throw new RuntimeException("Expected hasNextLong() to return false for invalid long input");
        }
    }

    private static void testHasNextLongWithEmptyInput() {
        Scanner sc = new Scanner("");
        boolean result = sc.hasNextLong();
        sc.close();
        if (result) {
            throw new RuntimeException("Expected hasNextLong() to return false for empty input");
        }
    }

    private static void testHasNextLongWithLeadingWhitespace() {
        Scanner sc = new Scanner("   42");
        boolean result = sc.hasNextLong();
        sc.close();
        if (!result) {
            throw new RuntimeException("Expected hasNextLong() to return true for input with leading whitespace");
        }
    }

    private static void testHasNextLongWithNegativeLong() {
        Scanner sc = new Scanner("-9876543210");
        boolean result = sc.hasNextLong();
        sc.close();
        if (!result) {
            throw new RuntimeException("Expected hasNextLong() to return true for negative long input");
        }
    }
}
